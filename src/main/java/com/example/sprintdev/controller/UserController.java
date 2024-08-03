package com.example.sprintdev.controller;

import com.example.sprintdev.dao.UserDAO;
import com.example.sprintdev.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDAO getUserInfo() {
        return this.userService.getSelf();
    }
}
