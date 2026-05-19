package com.grocery.assistant.controller;

import com.grocery.assistant.dto.SearchRequest;
import com.grocery.assistant.dto.SearchResponse;
import com.grocery.assistant.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * Natural-language product search powered by Llama 3.2 (Ollama).
     * Example: "I want something healthy for breakfast, high protein"
     */
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponse search(@Valid @RequestBody SearchRequest request) {
        return searchService.search(request.getQuery(), request.getLimit());
    }
}
