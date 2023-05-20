package com.bread.timedeal.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @Test
    void 사용자_상품을_등록() {
        User user = new User(Role.ADMIN);
        Product product = user.register(new Product(), new Quantity().plus(1L));

        assertThat(product.quantity()).isEqualTo(1);
    }

    @Test
    void 사용자_상품을_0개_등록() {
        User user = new User(Role.ADMIN);

        assertThatThrownBy(() -> {
            user.register(new Product(), new Quantity().plus(0L));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사용자_권한_상품_등록_권한_없음() {
        User user = new User(Role.USER);

        assertThatThrownBy(() -> {
            user.register(new Product(), new Quantity().plus(0L));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
