package com.bread.timedeal.service;

import static com.bread.timedeal.constants.Constants.NOW;
import static com.bread.timedeal.constants.Constants.TEST_PRODUCT_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bread.timedeal.constants.Constants;
import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.TimeSale;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.repository.ProductRepository;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;
  @InjectMocks
  private ProductService productService;

  @Test
  void 상품_생성() {
    ProductCreateRequest productCreateRequest = new ProductCreateRequest(10,
        NOW, TEST_PRODUCT_NAME);

    Product product = new Product(new Stock(10), new TimeSale(NOW), TEST_PRODUCT_NAME);

    when(productRepository.save(any(Product.class))).thenReturn(product);
    ProductCreateResponse createdProduct = productService.create(productCreateRequest);

    assertAll("상품 생성",
        () -> assertThat(createdProduct.name()).isEqualTo(TEST_PRODUCT_NAME),
        () -> assertThat(createdProduct.time()).isEqualTo(NOW.toString())
    );
  }

  @Test
  void 재고_증가() {
    Long productId = 1L;
    int currentStock = 10;
    int stockToAdd = 5;

    Product product = new Product(new Stock(10), new TimeSale(NOW), TEST_PRODUCT_NAME);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    productService.increaseStock(productId, stockToAdd);

    assertThat(product.count()).isEqualTo(currentStock + stockToAdd);
  }

  @Test
  void 재고_증가_상품_없을_때() {
    Long productId = 1L;
    int stockToAdd = 5;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> productService.increaseStock(productId, stockToAdd));
  }
}