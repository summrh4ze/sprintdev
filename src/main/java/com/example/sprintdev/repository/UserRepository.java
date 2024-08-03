package com.example.sprintdev.repository;

import com.example.sprintdev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
