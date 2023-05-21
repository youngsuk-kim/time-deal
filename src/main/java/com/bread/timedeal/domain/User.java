package com.bread.timedeal.domain;

import java.time.LocalDateTime;

public class User {

    private final Role role;

    public User(Role role) {
        this.role = role;
    }

    public Product register(Product product, Stock stock) {
        this.role.checkRoleUser();

        return product.add(stock);
    }

    public void buy(Product product, Stock stock) {
        product.decrease(stock, now());
    }

    protected LocalDateTime now() {
        return LocalDateTime.now();
    }
}
