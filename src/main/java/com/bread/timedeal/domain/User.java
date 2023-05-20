package com.bread.timedeal.domain;

public class User {

    private Role role;

    public User(Role role) {
        this.role = role;
    }

    public Product register(Product product, Quantity quantity) {
        if (this.role.notAdmin()) {
            throw new IllegalArgumentException();
        }
        return product.add(quantity);
    }
}
