package com.bread.timedeal.dto;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.TimeSale;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ProductCreateRequest(
    int stock,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime time,
    String name
) {
    public Product toEntity(ProductCreateRequest request) {
        return new Product(new Stock(request.stock), new TimeSale(time), name);
    }
}
