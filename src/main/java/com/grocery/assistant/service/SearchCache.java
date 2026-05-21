package com.grocery.assistant.service;

import com.grocery.assistant.dto.SearchResponse;

import java.util.Optional;

public interface SearchCache {

    Optional<SearchResponse> get(String query);

    void put(String query, SearchResponse response);
}
