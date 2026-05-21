package com.grocery.assistant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private boolean enabled = true;
    private int recommendationTtlMinutes = 30;
}
