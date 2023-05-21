package com.bread.timedeal.controller;

import com.bread.timedeal.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users/buy/{productId}")
  public void buy(@PathVariable Long productId, @RequestParam int quantity,
      @RequestParam Long userId) {
    userService.buy(productId, quantity, userId);
  }
}
