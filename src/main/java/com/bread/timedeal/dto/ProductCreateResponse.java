package com.bread.timedeal.dto;

import com.bread.timedeal.domain.Product;

import java.time.LocalDateTime;

public record ProductCreateResponse(int stock, LocalDateTime time, String name) {

  public static ProductCreateResponse of(Product product) {
    return new ProductCreateResponse(product.count(), product.endTime(), product.getName());
  }
}
