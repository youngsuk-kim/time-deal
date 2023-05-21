package com.bread.timedeal.repository;

import com.bread.timedeal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
