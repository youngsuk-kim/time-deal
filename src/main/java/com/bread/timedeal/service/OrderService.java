package com.bread.timedeal.service;


import com.bread.timedeal.domain.Order;
import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.User;
import com.bread.timedeal.dto.OrderRequest;
import com.bread.timedeal.repository.OrderRepository;
import com.bread.timedeal.repository.ProductRepository;
import com.bread.timedeal.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;

  public OrderService(ProductRepository productRepository, UserRepository userRepository,
      OrderRepository orderRepository) {
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  @Transactional
  public void order(OrderRequest orderRequest) {
    log.info("주문 데이터 {}", orderRequest.toString());

    User user = userRepository.findById(orderRequest.userId())
        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

    Product product = productRepository.findById(orderRequest.productId())
        .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));

    orderRepository.save(Order.make(product, orderRequest.count(), user));

    log.info("주문 완료 유저 아이디 {}, 제품명 {}", user.getId(), product.getName());
  }

  @Transactional
  public void decrease(Long id, int stock) {
    Product product = productRepository.findWithOptimisticLockById(id)
        .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));

        product.decrease(new Stock(stock), LocalDateTime.now());
        productRepository.saveAndFlush(product);
  }


}
