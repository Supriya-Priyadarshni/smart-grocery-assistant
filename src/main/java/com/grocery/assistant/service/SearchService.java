package com.grocery.assistant.service;

import com.grocery.assistant.dto.LlmFilterCriteria;
import com.grocery.assistant.dto.ProductDto;
import com.grocery.assistant.dto.SearchResponse;
import com.grocery.assistant.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 20;

    private final SearchCache cache;
    private final LlmService llmService;
    private final ProductService productService;

    public SearchResponse search(String query, Integer limit) {
        int resultLimit = normalizeLimit(limit);

        return cache.get(query)
                .map(cached -> SearchResponse.builder()
                        .query(query)
                        .reasoning(cached.getReasoning())
                        .products(cached.getProducts())
                        .cached(true)
                        .tookMs(0)
                        .build())
                .orElseGet(() -> computeAndCache(query, resultLimit));
    }

    private SearchResponse computeAndCache(String query, int limit) {
        long start = System.currentTimeMillis();

        List<Product> catalog = productService.findAllInStock();
        LlmFilterCriteria criteria = llmService.parseQuery(query, catalog);
        List<ProductDto> products = productService.findByCriteria(criteria, limit);

        String reasoning = criteria.getReasoning() != null
                ? criteria.getReasoning()
                : "Products matched from your query.";

        SearchResponse response = SearchResponse.builder()
                .query(query)
                .reasoning(reasoning)
                .products(products)
                .cached(false)
                .tookMs(System.currentTimeMillis() - start)
                .build();

        cache.put(query, response);
        return response;
    }

    private int normalizeLimit(Integer limit) {
        if (limit == null || limit < 1) {
            return DEFAULT_LIMIT;
        }
        return Math.min(limit, MAX_LIMIT);
    }
}
