package com.bread.timedeal.domain;

public enum Role {
  ADMIN, USER;

  void checkRoleUser() {
    if (this == USER) {
      throw new IllegalArgumentException();
    }
  }
}
