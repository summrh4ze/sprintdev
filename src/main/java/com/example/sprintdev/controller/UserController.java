package com.example.sprintdev.controller;

import com.example.sprintdev.dto.UserDTO;
import com.example.sprintdev.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDTO getUserInfo() {
        return this.userService.getSelf();
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        return this.userService.getAll();
    }
}
