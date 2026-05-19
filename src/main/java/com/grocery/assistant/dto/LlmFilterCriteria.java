package com.grocery.assistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LlmFilterCriteria {
    private String mealTime;
    private BigDecimal minProteinG;
    private BigDecimal maxPriceInr;
    private Boolean vegetarianOnly;
    private List<String> tags = new ArrayList<>();
    private String sortBy;
    private String reasoning;
}
