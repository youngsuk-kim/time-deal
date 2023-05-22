package com.bread.timedeal.service;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.repository.ProductRepository;
import java.time.LocalDateTime;
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

  @Transactional
  public void increaseStock(Long productId, int stock) {
    Product foundProduct = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품이 없습니다"));
    foundProduct.add(new Stock(stock));
  }

  @Transactional
  public void decreaseStock(Long productId, int stock) {
    Product foundProduct = productRepository.findByIdPessimisticLock(productId);
    foundProduct.decrease(new Stock(stock), LocalDateTime.now());
  }

}
