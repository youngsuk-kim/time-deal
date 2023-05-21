package com.bread.timedeal.domain;

public final class Quantity {
    private int value;

    public Quantity(int value) {
        this.value = value;
    }

    public Quantity plus(Quantity quantity) {
        int count = quantity.count();

        if (count <= 0) {
            throw new IllegalArgumentException();
        }

        this.value = this.value + count;

        return this;
    }

    public int count() {
        return this.value;
    }
}
