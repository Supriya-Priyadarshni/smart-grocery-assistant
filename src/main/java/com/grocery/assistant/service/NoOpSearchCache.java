package com.grocery.assistant.service;

import com.grocery.assistant.dto.SearchResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class NoOpSearchCache implements SearchCache {

    @Override
    public Optional<SearchResponse> get(String query) {
        return Optional.empty();
    }

    @Override
    public void put(String query, SearchResponse response) {
        // no-op when caching is disabled
    }
}
