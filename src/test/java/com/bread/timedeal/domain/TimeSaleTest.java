package com.bread.timedeal.domain;


import static com.bread.timedeal.Constants.NOW;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class TimeSaleTest {

  @Test
  void 할인_시간_경과() {
    LocalDateTime now = NOW;
    LocalDateTime saleEndTime = LocalDateTime.of(2023, 5, 24, 7, 6);

    TimeSale timeSale = new TimeSale(saleEndTime);

    Assertions.assertTrue(timeSale.timeOver(now));
  }
}