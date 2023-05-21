package com.bread.timedeal.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public final class Stock {
    private int stock;

    public Stock(int value) {
        this.stock = value;
    }

    public Stock() {}

    public Stock plus(Stock stock) {
        int count = validate(stock);

        synchronized (this) {
            this.stock = this.stock + count;
        }

        return this;
    }

    public Stock minus(Stock stock, LocalDateTime overTime) {
        int count = validate(stock);

        synchronized (this) {
            this.stock = this.stock - count;
        }

        return this;
    }

    private static int validate(Stock stock) {
        int count = stock.count();

        if (count <= 0) {
            throw new IllegalArgumentException();
        }
        return count;
    }

    public int count() {
        return this.stock;
    }
}
