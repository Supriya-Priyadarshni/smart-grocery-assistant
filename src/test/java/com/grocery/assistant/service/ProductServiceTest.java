package com.grocery.assistant.service;

import com.grocery.assistant.dto.LlmFilterCriteria;
import com.grocery.assistant.dto.ProductDto;
import com.grocery.assistant.entity.Product;
import com.grocery.assistant.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findByCriteriaSortsByPriceAscending() {
        LlmFilterCriteria criteria = new LlmFilterCriteria();
        criteria.setSortBy("price");

        when(productRepository.findByFilters(null, null, null, null))
                .thenReturn(List.of(
                        product("B", "120", "10"),
                        product("A", "65", "8"),
                        product("C", "90", "12")
                ));

        List<ProductDto> result = productService.findByCriteria(criteria, 10);

        assertThat(result).extracting(ProductDto::getName).containsExactly("A", "C", "B");
    }

    @Test
    void findByCriteriaSortsByProteinDescending() {
        LlmFilterCriteria criteria = new LlmFilterCriteria();
        criteria.setSortBy("protein");

        when(productRepository.findByFilters(null, null, null, null))
                .thenReturn(List.of(
                        product("Low", "50", "5"),
                        product("High", "120", "18"),
                        product("Mid", "90", "10")
                ));

        List<ProductDto> result = productService.findByCriteria(criteria, 10);

        assertThat(result).extracting(ProductDto::getName).containsExactly("High", "Mid", "Low");
    }

    @Test
    void findByCriteriaRetriesWithoutMealTagWhenFirstQueryReturnsEmpty() {
        LlmFilterCriteria criteria = new LlmFilterCriteria();
        criteria.setMealTime("breakfast");
        criteria.setMinProteinG(BigDecimal.valueOf(8));

        when(productRepository.findByFilters(eq(BigDecimal.valueOf(8)), isNull(), isNull(), eq("breakfast")))
                .thenReturn(List.of());
        when(productRepository.findByFilters(eq(BigDecimal.valueOf(8)), isNull(), isNull(), isNull()))
                .thenReturn(List.of(product("Fallback", "80", "9")));

        List<ProductDto> result = productService.findByCriteria(criteria, 10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Fallback");

        InOrder callOrder = inOrder(productRepository);
        callOrder.verify(productRepository).findByFilters(eq(BigDecimal.valueOf(8)), isNull(), isNull(), eq("breakfast"));
        callOrder.verify(productRepository).findByFilters(eq(BigDecimal.valueOf(8)), isNull(), isNull(), isNull());
    }

    private Product product(String name, String priceInr, String proteinG) {
        return Product.builder()
                .name(name)
                .priceInr(new BigDecimal(priceInr))
                .proteinG(new BigDecimal(proteinG))
                .stockQuantity(10)
                .build();
    }
}
