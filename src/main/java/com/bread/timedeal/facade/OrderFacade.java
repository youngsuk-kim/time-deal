package com.bread.timedeal.facade;

import com.bread.timedeal.dto.OrderRequest;
import com.bread.timedeal.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {

  private final OrderService orderService;

  public OrderFacade(OrderService orderService) {
    this.orderService = orderService;
  }

  public void execute(OrderRequest request) throws InterruptedException {
    orderService.order(request);

    while (true) {
      try {
        orderService.decrease(request.productId(), request.count());
        break;
      } catch (Exception e) {
        Thread.sleep(50);
      }
    }
  }

}
