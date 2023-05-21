package com.bread.timedeal.domain;

import java.time.LocalDateTime;

public class TimeSale {

    private final LocalDateTime saleEndTime;

    public TimeSale(LocalDateTime saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public boolean timeOver(LocalDateTime now) {
        return now.isAfter(saleEndTime);
    }
}
