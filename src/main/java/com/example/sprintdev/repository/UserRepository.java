package com.example.sprintdev.repository;

import com.example.sprintdev.model.User;
import com.example.sprintdev.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("select u from User u join u.roles role where role in :roles")
    List<User> findByRoles(@Param("roles") List<UserRole> roles);
}
