package com.bread.timedeal.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

  public Order(User user, Product product, int count) {
    this.user = user;
    this.product = product;
    this.count = count;
    this.orderStatus = OrderStatus.PAID;
  }

  public Order() {}

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Product product;

  @Column
  private int count;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  public static Order make(Product product, int stock, User user) {
    return new Order(user, product, stock);
  }
}
