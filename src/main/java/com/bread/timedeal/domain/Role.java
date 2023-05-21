package com.bread.timedeal.domain;

public enum Role {
    ADMIN, USER;

    boolean notAdmin() {
        return this == USER;
    }
}
