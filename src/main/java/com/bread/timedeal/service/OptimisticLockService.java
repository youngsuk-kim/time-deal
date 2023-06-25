package com.bread.timedeal.service;

import org.springframework.stereotype.Service;

@Service
public class OptimisticLockService {

  private final OrderService orderService;
  public OptimisticLockService(OrderService orderService) {
    this.orderService = orderService;
  }

  public void decrease(Long id, int stock) throws InterruptedException {
    while (true) {
      try {
        orderService.decrease(id, stock);
        break;
      } catch (Exception e) {
        Thread.sleep(50);
      }
    }
  }
}
