package com.bread.timedeal.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TimeSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime saleEndTime;

    public TimeSale(LocalDateTime saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public TimeSale() {
    }

    public boolean timeOver(LocalDateTime now) {
        return now.isAfter(saleEndTime);
    }

    public LocalDateTime getSaleEndTime() {
        return saleEndTime;
    }
}
