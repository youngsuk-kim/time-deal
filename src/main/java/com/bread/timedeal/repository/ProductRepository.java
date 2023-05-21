package com.bread.timedeal.repository;

import com.bread.timedeal.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
