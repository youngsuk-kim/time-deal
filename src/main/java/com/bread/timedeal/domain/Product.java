package com.bread.timedeal.domain;

import java.time.LocalDateTime;

public class Product {

    private final Stock stock = new Stock(0);

    private final TimeSale timeSale = new TimeSale(LocalDateTime.of(2023, 5, 24, 7, 7));

    public Product add(Stock stock) {
        this.stock.plus(stock);
        return this;
    }

    public int count() {
        return this.stock.count();
    }

    public Product decrease(Stock stock, LocalDateTime now) {
        if (timeSale.timeOver(now)) {
            throw new RuntimeException("할인 기간이 종료 된 상품 입니다.");
        }

        this.stock.minus(stock, now);

        return this;
    }
}
