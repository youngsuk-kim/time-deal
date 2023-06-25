package com.bread.timedeal.controller;

import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.dto.ProductResponse;
import com.bread.timedeal.dto.ProductUpdateRequest;
import com.bread.timedeal.service.ProductService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    return productService.create(request);
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

  /**
   * 상품 업데이트
   *
   * @param request
   * @param productId
   */
  @PutMapping("/products/{productId}")
  public void update(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
    productService.update(request, productId);
  }

  /**
   * 상품 리스트 가져오기
   *
   * @param pageable
   * @return
   */
  @GetMapping("/products")
  public List<ProductResponse> list(Pageable pageable) {
    return productService.list(pageable).stream()
        .map(product -> new ProductResponse(product.getId(),
            product.getName(), product.count(), product.endTime())).toList();
  }

  /**
   * 상품 제거
   *
   * @param productId
   */
  @DeleteMapping("/products/{productId}")
  public void delete(@PathVariable Long productId) {
    productService.delete(productId);
  }
}
