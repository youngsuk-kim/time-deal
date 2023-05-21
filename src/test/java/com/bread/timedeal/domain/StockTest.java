package com.bread.timedeal.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class StockTest {
    @Test
    void 재고_증가() {
        Stock stock1 = new Stock(10);
        Stock stock2 = new Stock(5);
        Stock result = stock1.plus(stock2);

        Assertions.assertEquals(15, result.count());
    }
    @Test
    void 재고_감소() {
        Stock stock1 = new Stock(10);
        Stock stock2 = new Stock(5);
        LocalDateTime overTime = LocalDateTime.now().plusHours(1);
        Stock result = stock1.minus(stock2, overTime);

        Assertions.assertEquals(5, result.count());
    }
    @Test
    void 재고_음수_감소() {
        Stock stock1 = new Stock(10);
        Stock stock2 = new Stock(-1);
        LocalDateTime overTime = LocalDateTime.now().plusHours(1);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> stock1.minus(stock2, overTime));
    }
}
