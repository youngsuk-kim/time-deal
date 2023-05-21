package com.bread.timedeal.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoleTest {

  @Test
  void 유저_롤_체크() {
    Assertions.assertThrows(IllegalArgumentException.class,
        Role.USER::checkRoleUser);

    Assertions.assertDoesNotThrow(Role.ADMIN::checkRoleUser);
  }
}
