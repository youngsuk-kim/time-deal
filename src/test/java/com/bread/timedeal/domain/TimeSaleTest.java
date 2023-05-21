package com.bread.timedeal.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class TimeSaleTest {

  @Test
  void 할인_시간_경과() {
    LocalDateTime now = LocalDateTime.of(2023, 5, 24, 7, 8);
    LocalDateTime saleEndTime = LocalDateTime.of(2023, 5, 24, 7, 7);

    TimeSale timeSale = new TimeSale(saleEndTime);

    Assertions.assertTrue(timeSale.timeOver(now));
  }
}