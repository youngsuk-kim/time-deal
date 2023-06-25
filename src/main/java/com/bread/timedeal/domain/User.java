package com.bread.timedeal.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Role userRole;

  public User(Role userRole) {
    this.userRole = userRole;
  }

  public User() {}

  public Order order(Product product, int count) {
    return Order.make(product, count, this);
  }

  public Product register(Product product, Stock stock) {
    this.userRole.checkRoleUser();

    return product.add(stock);
  }

  public void buy(Product product, Stock stock) {
    product.decrease(stock, now());
  }

  protected LocalDateTime now() {
    return LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }
}
