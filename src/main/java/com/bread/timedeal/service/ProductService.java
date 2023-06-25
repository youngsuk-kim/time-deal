package com.bread.timedeal.service;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.dto.ProductUpdateRequest;
import com.bread.timedeal.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public ProductCreateResponse create(ProductCreateRequest request) {
    return ProductCreateResponse.of(productRepository.save(request.toEntity(request)));
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

  @Transactional
  public void update(ProductUpdateRequest request, Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품이 없습니다"));

    product.update(request);
  }

  @Transactional
  public void delete(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품이 없습니다"));

    product.delete();
  }

  @Transactional(readOnly = true)
  public List<Product> list(Pageable pageable) {
    return productRepository.findAll(pageable).toList();
  }
}
