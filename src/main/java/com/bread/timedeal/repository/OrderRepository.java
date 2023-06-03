package com.bread.timedeal.repository;

import com.bread.timedeal.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
