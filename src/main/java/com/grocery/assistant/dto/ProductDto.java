package com.grocery.assistant.dto;

import com.grocery.assistant.entity.Product;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class ProductDto {
    Long id;
    String name;
    String brand;
    String category;
    BigDecimal priceInr;
    BigDecimal proteinG;
    BigDecimal carbsG;
    BigDecimal fatG;
    Integer calories;
    String mealTags;
    String description;
    boolean inStock;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .category(product.getCategory() != null ? product.getCategory().getName() : null)
                .priceInr(product.getPriceInr())
                .proteinG(product.getProteinG())
                .carbsG(product.getCarbsG())
                .fatG(product.getFatG())
                .calories(product.getCalories())
                .mealTags(product.getMealTags())
                .description(product.getDescription())
                .inStock(product.getStockQuantity() != null && product.getStockQuantity() > 0)
                .build();
    }
}
