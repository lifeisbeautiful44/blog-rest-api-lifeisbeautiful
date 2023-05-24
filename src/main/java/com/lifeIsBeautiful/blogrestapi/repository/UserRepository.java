package com.lifeIsBeautiful.blogrestapi.repository;

import com.lifeIsBeautiful.blogrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /*Custom Query Method
    Optional<User> findByName(String name);*/

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String Username);
    Boolean existsByEmail(String email);
}
