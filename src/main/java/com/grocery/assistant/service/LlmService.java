package com.grocery.assistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.assistant.config.LlmProperties;
import com.grocery.assistant.dto.LlmFilterCriteria;
import com.grocery.assistant.entity.Product;
import com.grocery.assistant.exception.LlmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmService {

    private static final String SYSTEM_PROMPT = """
            You are a grocery recommendation assistant for an Indian quick-commerce app.
            Given a natural-language shopping query, output ONLY valid JSON (no markdown) with:
            {
              "mealTime": "breakfast|lunch|dinner|snack|null",
              "minProteinG": number or null,
              "maxPriceInr": number or null,
              "vegetarianOnly": boolean or null,
              "tags": ["high-protein", "quick", ...] or [],
              "sortBy": "protein|price|null",
              "reasoning": "one sentence explaining your interpretation"
            }
            Infer dietary needs (e.g. high-protein breakfast → minProteinG: 8, mealTime: breakfast).
            """;

    private final LlmProperties llmProperties;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public LlmFilterCriteria parseQuery(String userQuery, List<Product> catalogSample) {
        String catalogContext = buildCatalogContext(catalogSample);
        String userMessage = "Query: \"" + userQuery + "\"\n\nAvailable product tags sample:\n" + catalogContext;

        try {
            String raw = "openai".equalsIgnoreCase(llmProperties.getProvider())
                    ? callOpenAi(userMessage)
                    : callOllama(userMessage);
            return parseCriteria(raw);
        } catch (Exception e) {
            log.warn("LLM call failed, using rule-based fallback: {}", e.getMessage());
            return fallbackCriteria(userQuery);
        }
    }

    private String buildCatalogContext(List<Product> products) {
        return products.stream()
                .limit(20)
                .map(p -> "- " + p.getName() + " [" + p.getMealTags() + "] protein=" + p.getProteinG() + "g")
                .collect(Collectors.joining("\n"));
    }

    private String callOllama(String userMessage) {
        WebClient client = webClientBuilder.baseUrl(llmProperties.getOllama().getBaseUrl()).build();

        Map<String, Object> body = Map.of(
                "model", llmProperties.getOllama().getModel(),
                "stream", false,
                "format", "json",
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", userMessage)
                )
        );

        JsonNode response = client.post()
                .uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null || !response.has("message")) {
            throw new LlmException("Empty Ollama response");
        }
        return response.get("message").get("content").asText();
    }

    private String callOpenAi(String userMessage) {
        if (llmProperties.getOpenai().getApiKey() == null || llmProperties.getOpenai().getApiKey().isBlank()) {
            throw new LlmException("OPENAI_API_KEY is not configured");
        }

        WebClient client = webClientBuilder.baseUrl(llmProperties.getOpenai().getBaseUrl()).build();

        Map<String, Object> body = Map.of(
                "model", llmProperties.getOpenai().getModel(),
                "response_format", Map.of("type", "json_object"),
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", userMessage)
                )
        );

        JsonNode response = client.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + llmProperties.getOpenai().getApiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null || !response.has("choices")) {
            throw new LlmException("Empty OpenAI response");
        }
        return response.get("choices").get(0).get("message").get("content").asText();
    }

    private LlmFilterCriteria parseCriteria(String raw) throws Exception {
        String json = extractJson(raw);
        return objectMapper.readValue(json, LlmFilterCriteria.class);
    }

    private String extractJson(String raw) {
        int start = raw.indexOf('{');
        int end = raw.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return raw.substring(start, end + 1);
        }
        return raw;
    }

    private LlmFilterCriteria fallbackCriteria(String query) {
        String lower = query.toLowerCase();
        LlmFilterCriteria criteria = new LlmFilterCriteria();
        criteria.setReasoning("Matched using keyword rules (LLM unavailable)");

        if (lower.contains("breakfast")) {
            criteria.setMealTime("breakfast");
        } else if (lower.contains("lunch")) {
            criteria.setMealTime("lunch");
        } else if (lower.contains("dinner")) {
            criteria.setMealTime("dinner");
        } else if (lower.contains("snack")) {
            criteria.setMealTime("snack");
        }

        if (lower.contains("high-protein") || lower.contains("high protein") || lower.contains("protein")) {
            criteria.setMinProteinG(java.math.BigDecimal.valueOf(8));
            criteria.getTags().add("high-protein");
        }

        if (lower.contains("vegetarian") || lower.contains("veg ")) {
            criteria.setVegetarianOnly(true);
        }

        if (lower.contains("cheap") || lower.contains("budget") || lower.contains("under")) {
            criteria.setMaxPriceInr(java.math.BigDecimal.valueOf(100));
        }

        criteria.setSortBy("protein");
        return criteria;
    }
}
