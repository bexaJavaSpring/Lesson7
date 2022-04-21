package com.example.lesson7.repository;

import com.example.lesson7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String name);

    User getByUserName(String username);
}
