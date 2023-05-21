package com.bread.timedeal.domain;

public class Product {

    private final Quantity quantity = new Quantity(0);

    public Product add(Quantity quantity) {
        this.quantity.plus(quantity);
        return this;
    }

    public int count() {
        return this.quantity.count();
    }
}
