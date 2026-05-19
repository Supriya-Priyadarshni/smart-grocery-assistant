package com.grocery.assistant.service;

import com.grocery.assistant.dto.LlmFilterCriteria;
import com.grocery.assistant.dto.ProductDto;
import com.grocery.assistant.entity.Product;
import com.grocery.assistant.exception.ProductNotFoundException;
import com.grocery.assistant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAllInStock() {
        return productRepository.findAllInStock();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> listAll() {
        return productRepository.findAllWithCategory().stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return productRepository.findByIdWithCategory(id)
                .map(ProductDto::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream()
                .map(ProductDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findByCriteria(LlmFilterCriteria criteria, int limit) {
        String mealTag = criteria.getMealTime();

        List<Product> products = productRepository.findByFilters(
                criteria.getMinProteinG(),
                criteria.getMaxPriceInr(),
                criteria.getVegetarianOnly(),
                mealTag
        );

        if (products.isEmpty() && mealTag != null) {
            products = productRepository.findByFilters(
                    criteria.getMinProteinG(),
                    criteria.getMaxPriceInr(),
                    criteria.getVegetarianOnly(),
                    null
            );
        }

        if ("price".equalsIgnoreCase(criteria.getSortBy())) {
            products.sort(Comparator.comparing(Product::getPriceInr));
        }

        return products.stream()
                .limit(limit)
                .map(ProductDto::from)
                .toList();
    }
}
