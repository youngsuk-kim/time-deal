package com.bread.timedeal.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class UserTest {

  private final Role adminRole = Role.ADMIN;
  private final Role nonAdminRole = Role.USER;
  private Product testProduct;
  private Stock testStock;
  private User testAdmin;
  private User testCustomer;

  @BeforeEach
  public void setup() {
    testStock = new Stock(10);
    testAdmin = new User(adminRole);
    testCustomer = new User(nonAdminRole);
    testProduct = new Product(testStock, new TimeSale(LocalDateTime.of(2023, 5, 24, 7, 7)),
        "testProductName");
  }

  @Test
  void 재고_증가() {
    Product result = testAdmin.register(testProduct, testStock);
    Assertions.assertEquals(testProduct, result);
    Assertions.assertEquals(20, result.count());
  }

  @Test
  void 유저_롤_재고_증가() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> testCustomer.register(testProduct, testStock));
  }

  @Test
  void 구매() {
    testProduct.add(testStock);
    testAdmin.buy(testProduct, new Stock(5));
    Assertions.assertEquals(15, testProduct.count());
  }

  @Test
  void 현재_시간() {
    User user = new User(Role.ADMIN) {
      @Override
      protected LocalDateTime now() {
        return LocalDateTime.of(2023, 5, 24, 7, 6);
      }
    };

    LocalDateTime result = testAdmin.now();
    Assertions.assertTrue(result.isBefore(user.now()) || result.isEqual(user.now()));
  }
}
