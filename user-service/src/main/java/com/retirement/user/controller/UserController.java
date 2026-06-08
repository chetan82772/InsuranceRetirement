package com.retirement.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retirement.user.entity.User;
import com.retirement.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }
}