package com.grocery.assistant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.assistant.config.LlmProperties;
import com.grocery.assistant.dto.LlmFilterCriteria;
import com.grocery.assistant.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LlmServiceFallbackTest {

    @Test
    void fallbackParsesHighProteinBreakfast() {
        LlmProperties props = new LlmProperties();
        props.setProvider("ollama");
        props.getOllama().setBaseUrl("http://invalid:1");

        LlmService service = new LlmService(props, WebClient.builder(), new ObjectMapper());

        LlmFilterCriteria criteria = service.parseQuery(
                "I need something high-protein for breakfast",
                List.of(Product.builder().name("Eggs").proteinG(BigDecimal.TEN).mealTags("breakfast").build())
        );

        assertThat(criteria.getMealTime()).isEqualTo("breakfast");
        assertThat(criteria.getMinProteinG()).isGreaterThanOrEqualTo(BigDecimal.valueOf(8));
    }
}
