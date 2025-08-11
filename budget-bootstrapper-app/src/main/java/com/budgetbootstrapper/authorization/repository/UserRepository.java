package com.budgetbootstrapper.authorization.repository;

import com.budgetbootstrapper.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserEntityByUsername(String username);

  Boolean existsByUsername(String username);
}
