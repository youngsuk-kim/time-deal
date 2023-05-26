package com.bread.timedeal;

import static com.bread.timedeal.constants.Constants.NOW;
import static com.bread.timedeal.constants.Constants.TEST_PRODUCT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.TimeSale;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.repository.ProductRepository;
import com.bread.timedeal.service.ProductService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceIntegrationTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @Test
  void 재고_감소_동시성() throws InterruptedException {
    int threadCount = 100;

    Product saveProduct = productService.create(
        new ProductCreateRequest(threadCount, new TimeSale(NOW).getSaleEndTime(),
            TEST_PRODUCT_NAME));

    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executorService.submit(() -> {
        try {
          productService.decreaseStock(saveProduct.getId(), 1);
        } catch (Exception e) {
          System.out.println(e);
        }
        finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    Product product = productRepository.findById(saveProduct.getId()).orElseThrow();

    assertThat(product.count()).isZero();
  }
}