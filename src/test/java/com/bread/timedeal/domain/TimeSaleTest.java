package com.bread.timedeal.domain;


import static com.bread.timedeal.constants.Constants.NOW;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class TimeSaleTest {

  @Test
  void 할인_시간_경과() {
    LocalDateTime now = NOW;
    LocalDateTime saleEndTime = LocalDateTime.of(2023, 5, 24, 7, 6);

    TimeSale timeSale = new TimeSale(saleEndTime);

    assertTrue(timeSale.timeOver(now));
  }
}