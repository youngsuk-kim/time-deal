package com.bread.timedeal.service;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(ProductCreateRequest request) {
        return productRepository.save(request.toEntity(request));
    }
}
