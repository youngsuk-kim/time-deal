package com.bread.timedeal.service;

import static com.bread.timedeal.constants.Constants.NOW;
import static com.bread.timedeal.constants.Constants.TEST_PRODUCT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Role;
import com.bread.timedeal.domain.TimeSale;
import com.bread.timedeal.dto.OrderRequest;
import com.bread.timedeal.dto.ProductCreateRequest;
import com.bread.timedeal.dto.ProductCreateResponse;
import com.bread.timedeal.facade.OrderFacade;
import com.bread.timedeal.repository.ProductRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceTest {

  private static final int THREAD_COUNT = 100;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderFacade orderFacade;

  @Autowired
  private UserService userService;

  @Test
  void 재고_감소_동시성() throws InterruptedException {

    ProductCreateResponse saveProduct = productService.create(
        new ProductCreateRequest(THREAD_COUNT, new TimeSale(NOW).getSaleEndTime(),
            TEST_PRODUCT_NAME));

    userService.create(Role.ADMIN);

    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

    for (int i = 0; i < THREAD_COUNT; i++) {
      executorService.submit(() -> {
        try {
          orderFacade.execute(new OrderRequest(1L, 1L, 1));
        } catch (Exception e) {
          System.out.println(e);
        }
        finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    Product product = productRepository.findById(saveProduct.id()).orElseThrow();

    assertThat(product.count()).isZero();
  }
}
