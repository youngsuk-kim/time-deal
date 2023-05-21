package com.bread.timedeal.service;

import com.bread.timedeal.domain.Product;
import com.bread.timedeal.domain.Role;
import com.bread.timedeal.domain.Stock;
import com.bread.timedeal.domain.User;
import com.bread.timedeal.repository.ProductRepository;
import com.bread.timedeal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final ProductRepository productRepository;

  public UserService(UserRepository userRepository, ProductRepository productRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Transactional
  public void create(Role role) {
    userRepository.save(new User(role));
  }

  @Transactional
  public void buy(Long productId, int quantity, Long userId) {
    final User foundUser = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

    final Product foundProduct = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

    foundUser.buy(foundProduct, new Stock(quantity));
  }
}
