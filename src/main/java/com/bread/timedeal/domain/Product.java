package com.bread.timedeal.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    public Product(Stock stock, TimeSale timeSale, String name) {
        this.stock = stock;
        this.timeSale = timeSale;
        this.name = name;
    }

    public Product() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    private Stock stock = new Stock(1);

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private TimeSale timeSale;

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

    public LocalDateTime endTime() {
        return timeSale.getSaleEndTime();
    }

    public String getName() {
        return this.name;
    }
}
