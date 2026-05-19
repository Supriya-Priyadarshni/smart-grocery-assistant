package com.grocery.assistant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "price_inr", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceInr;

    @Column(name = "protein_g", precision = 6, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "carbs_g", precision = 6, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "fat_g", precision = 6, scale = 2)
    private BigDecimal fatG;

    private Integer calories;

    @Column(name = "fiber_g", precision = 6, scale = 2)
    private BigDecimal fiberG;

    @Column(name = "is_vegetarian")
    private Boolean vegetarian;

    @Column(name = "meal_tags", length = 255)
    private String mealTags;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
