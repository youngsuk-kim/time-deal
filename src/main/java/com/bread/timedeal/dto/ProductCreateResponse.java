package com.bread.timedeal.dto;

import com.bread.timedeal.domain.Product;

public record ProductCreateResponse(Long id, int stock, String time, String name) {

  public static ProductCreateResponse of(Product product) {
    return new ProductCreateResponse(product.getId(), product.count(), product.endTime().toString(), product.getName());
  }


}
