package com.bread.timedeal.domain;

import java.time.LocalDateTime;

public final class Stock {
    private int value;

    public Stock(int value) {
        this.value = value;
    }

    public Stock plus(Stock stock) {
        int count = validate(stock);

        synchronized (this) {
            this.value = this.value + count;
        }

        return this;
    }

    public Stock minus(Stock stock, LocalDateTime overTime) {
        int count = validate(stock);

        synchronized (this) {
            this.value = this.value - count;
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
        return this.value;
    }
}
