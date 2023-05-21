package com.bread.timedeal.dto;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.TimeSale;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.bread.timedeal.Constants.DATE_TIME_FORMAT;

public record ProductCreateRequest(
    int stock,
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    LocalDateTime time,
    String name
) {

  public Product toEntity(ProductCreateRequest request) {
    return new Product(new Stock(request.stock), new TimeSale(time), name);
  }
}
