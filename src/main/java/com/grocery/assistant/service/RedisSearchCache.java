package com.grocery.assistant.service;

import com.grocery.assistant.config.CacheProperties;
import com.grocery.assistant.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true", matchIfMissing = true)
public class RedisSearchCache implements SearchCache {

    private static final String KEY_PREFIX = "search:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheProperties cacheProperties;

    @Override
    public Optional<SearchResponse> get(String query) {
        try {
            Object cached = redisTemplate.opsForValue().get(cacheKey(query));
            if (cached instanceof SearchResponse response) {
                return Optional.of(response);
            }
        } catch (Exception e) {
            log.warn("Redis unavailable, skipping cache read: {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void put(String query, SearchResponse response) {
        try {
            redisTemplate.opsForValue().set(
                    cacheKey(query),
                    response,
                    Duration.ofMinutes(cacheProperties.getRecommendationTtlMinutes())
            );
        } catch (Exception e) {
            log.warn("Redis unavailable, skipping cache write: {}", e.getMessage());
        }
    }

    private String cacheKey(String query) {
        return KEY_PREFIX + sha256(query.trim().toLowerCase());
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(input.hashCode());
        }
    }
}
