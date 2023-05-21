package com.bread.timedeal.domain;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

  @Test
  void 사용자_상품을_등록() {
    User user = new User(Role.ADMIN);
    Product product = new Product();
    user.register(product, new Stock(1));

    assertThat(product.count()).isEqualTo(2);
  }

  @Test
  void 사용자_동시에_100개_등록() throws InterruptedException {
    User user = new User(Role.ADMIN);
    Product product = new Product();

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    CountDownLatch latch = new CountDownLatch(1000);

    for (int i = 0; i < 1000; i++) {
      executorService.submit(() -> {
        user.register(product, new Stock(1));
        latch.countDown();
      });
    }

    executorService.shutdown();
    latch.await();

    assertThat(product.count()).isEqualTo(1001);
  }

  @Test
  void 사용자_상품을_0개_등록() {
    User user = new User(Role.USER);
    Product product = new Product();

    assertThatThrownBy(() -> {
      user.register(product, new Stock(1));
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 사용자_권한_상품_등록_권한_없음() {
    User user = new User(Role.USER);
    Product product = new Product();

    assertThatThrownBy(() -> {
      user.register(product, new Stock(1));
    }).isInstanceOf(IllegalArgumentException.class);
  }
}
