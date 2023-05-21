package com.bread.timedeal.controller;

import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * 상품 생성
   *
   * @param request
   * @return
   */
  @PostMapping("/products")
  public ProductCreateResponse create(@RequestBody ProductCreateRequest request) {
    return ProductCreateResponse.of(productService.create(request));
  }

  /**
   * 상품 재고 증가
   *
   * @param productId
   * @param stock
   */
  @PostMapping("/products/{productId}/stock")
  public void increaseStock(@PathVariable Long productId, @RequestParam Integer stock) {
    productService.increaseStock(productId, stock);
  }
}
