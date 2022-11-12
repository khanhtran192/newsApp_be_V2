package com.newsApp_be.repository;

import com.newsApp_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAndEnable(String email, Boolean enable);

    Boolean existsByEmailAndEnable(String email, Boolean enable);
    Boolean existsByUsernameAndEmailAndEnable(String username, String email, Boolean enable);
}
