package com.bread.timedeal.domain;

public final class Quantity {

    private Long value = 0L;

    public Quantity() {}

    public Quantity plus(Long value) {
        if (value <= 0) {
            throw new IllegalArgumentException();
        }
        this.value += value;
        return this;
    }

    public Long count() {
        return value;
    }
}
