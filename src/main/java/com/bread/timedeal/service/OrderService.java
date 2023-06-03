package com.bread.timedeal.service;


import static com.bread.timedeal.Constants.FIRST_ELEMENT;
import static com.bread.timedeal.Constants.LAST_ELEMENT;
import static com.bread.timedeal.Constants.LAST_INDEX;
import static com.bread.timedeal.Constants.PUBLISH_SIZE;
import static com.bread.timedeal.Constants.TIME_SALE_ORDER_COUNT;
import static com.bread.timedeal.Constants.TIME_SALE_PRODUCT_NAME;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.User;
import com.bread.timedeal.dto.OrderRequest;
import com.bread.timedeal.repository.ProductRepository;
import com.bread.timedeal.repository.UserRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  public OrderService(
      ProductRepository productRepository,
      UserRepository userRepository,
      RedisTemplate<String, Object> redisTemplate) {
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.redisTemplate = redisTemplate;
  }

  @Transactional
  public void addQueue(OrderRequest orderRequest) {
    final long now = System.currentTimeMillis();

    User user = userRepository.findById(orderRequest.userId)
        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

    redisTemplate.opsForZSet().add(TIME_SALE_PRODUCT_NAME, user.getId(), (int) now);

    log.info("대기열에 추가 - {} ({}초)", orderRequest.userId, now);
  }

  @Transactional(readOnly = true)
  public void getOrder() {
    final long start = FIRST_ELEMENT;
    final long end = LAST_ELEMENT;

    Set<Object> userIdQueue = redisTemplate.opsForZSet().range(TIME_SALE_PRODUCT_NAME, start, end);

    for (Object userId : userIdQueue) {
      Long rank = redisTemplate.opsForZSet().rank(TIME_SALE_PRODUCT_NAME, userId);
      log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", userId, rank);
    }
  }

  @Transactional
  public void publish() {

    final long start = FIRST_ELEMENT;
    final long end = PUBLISH_SIZE - LAST_INDEX;

    Set<Object> userIdQueue = redisTemplate.opsForZSet().range("나이키", start, end);

    if (userIdQueue.isEmpty()) {
      log.info("주문한 고객이 없습니다");
    }

    for (Object userId : userIdQueue) {
      Product product = productRepository.findById(3L)
          .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

      User user = userRepository.findById((Long) userId)
          .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

      user.order(product, TIME_SALE_ORDER_COUNT);
      log.info("'{}'님의 {} 선착순 주문이 완료 되었습니다.", user.getId(), TIME_SALE_PRODUCT_NAME);
      redisTemplate.opsForZSet().remove(TIME_SALE_PRODUCT_NAME, userId);
    }
  }

}
