package com.bread.timedeal.domain;

public class Product {

    private Quantity quantity = new Quantity();

    public Product add(Quantity quantity) {
        this.quantity.plus(quantity.count());
        return this;
    }

    public Long quantity() {
        return this.quantity.count();
    }
}
