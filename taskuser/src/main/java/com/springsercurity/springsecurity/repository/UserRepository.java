package com.springsercurity.springsecurity.repository;

import com.springsercurity.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);
}
