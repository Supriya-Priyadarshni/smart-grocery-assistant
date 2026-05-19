package com.grocery.assistant.repository;

import com.grocery.assistant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

    @Query("""
            SELECT p FROM Product p
            JOIN FETCH p.category c
            WHERE LOWER(c.name) = LOWER(:categoryName)
            ORDER BY p.name
            """)
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.name")
    List<Product> findAllWithCategory();

    @Query("""
            SELECT p FROM Product p
            JOIN FETCH p.category c
            WHERE (:minProtein IS NULL OR p.proteinG >= :minProtein)
              AND (:maxPrice IS NULL OR p.priceInr <= :maxPrice)
              AND (:vegetarianOnly IS NULL OR :vegetarianOnly = FALSE OR p.vegetarian = TRUE)
              AND (:mealTag IS NULL OR LOWER(p.mealTags) LIKE LOWER(CONCAT('%', :mealTag, '%')))
              AND p.stockQuantity > 0
            ORDER BY p.proteinG DESC
            """)
    List<Product> findByFilters(
            @Param("minProtein") BigDecimal minProtein,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("vegetarianOnly") Boolean vegetarianOnly,
            @Param("mealTag") String mealTag);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.stockQuantity > 0")
    List<Product> findAllInStock();
}
