package com.bread.timedeal.repository;

import com.bread.timedeal.domain.Product;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Lock(value = LockModeType.PESSIMISTIC_WRITE)
  @Query(value = "select p from Product p where p.id = :id")
  Product findByIdPessimisticLock(@Param("id") Long id);

  @Lock(LockModeType.OPTIMISTIC)
  Optional<Product> findWithOptimisticLockById(Long id);
}
