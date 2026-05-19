package com.grocery.assistant.controller;

import com.grocery.assistant.dto.ProductDto;
import com.grocery.assistant.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> listProducts() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/category/{name}")
    public List<ProductDto> getByCategory(@PathVariable String name) {
        return productService.findByCategory(name);
    }
}
