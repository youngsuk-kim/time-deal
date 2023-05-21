package com.bread.timedeal.controller;

import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ProductCreateResponse create(@RequestBody ProductCreateRequest request) {
        return ProductCreateResponse.of(productService.create(request));
    }
}
