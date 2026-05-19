package com.grocery.assistant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "llm")
public class LlmProperties {
    private String provider = "ollama";
    private Ollama ollama = new Ollama();
    private OpenAi openai = new OpenAi();

    @Data
    public static class Ollama {
        private String baseUrl = "http://localhost:11434";
        private String model = "llama3.2";
    }

    @Data
    public static class OpenAi {
        private String apiKey = "";
        private String model = "gpt-4o-mini";
        private String baseUrl = "https://api.openai.com/v1";
    }
}
