package com.bread.timedeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StockTest {

  @Test
  void 재고_증가() {
    Stock stock1 = new Stock(10);
    Stock stock2 = new Stock(5);
    Stock result = stock1.plus(stock2);

    assertEquals(15, result.count());
  }

  @Test
  void 재고_감소() {
    Stock stock1 = new Stock(10);
    Stock stock2 = new Stock(5);
    Stock result = stock1.minus(stock2);

    assertEquals(5, result.count());
  }

  @Test
  void 재고_음수_감소() {
    Stock stock1 = new Stock(10);
    Stock stock2 = new Stock(-1);

    assertThrows(IllegalArgumentException.class,
        () -> stock1.minus(stock2));
  }
}
