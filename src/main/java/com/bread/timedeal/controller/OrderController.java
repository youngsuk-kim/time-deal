package com.bread.timedeal.controller;

import com.bread.timedeal.dto.OrderRequest;
import com.bread.timedeal.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * 주문하기
   *
   * @param orderRequest
   */
  @PostMapping("/orders")
  public void order(@RequestBody OrderRequest orderRequest) throws InterruptedException {
    orderService.order(orderRequest);
  }
}
