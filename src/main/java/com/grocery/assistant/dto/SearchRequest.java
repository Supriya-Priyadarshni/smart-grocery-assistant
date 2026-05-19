package com.grocery.assistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SearchRequest {

    @NotBlank(message = "Query cannot be empty")
    @Size(min = 3, max = 500, message = "Query must be between 3 and 500 characters")
    String query;

    Integer limit;
}
