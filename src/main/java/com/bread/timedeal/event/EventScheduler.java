package com.bread.timedeal.event;

import com.bread.timedeal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventScheduler {

  private final OrderService orderService;

  public EventScheduler(OrderService orderService) {
    this.orderService = orderService;
  }

  @Scheduled(fixedDelay = 1000)
  public void timeDealEventScheduler() {
    orderService.publish();
    orderService.getOrder();
  }
}

