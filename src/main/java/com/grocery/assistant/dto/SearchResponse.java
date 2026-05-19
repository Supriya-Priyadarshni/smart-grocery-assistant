package com.grocery.assistant.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class SearchResponse {
    String query;
    String reasoning;
    List<ProductDto> products;
    boolean cached;
    long tookMs;
}
