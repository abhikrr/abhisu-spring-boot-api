package com.abhisu.abhisu_spring_boot_api.controller;

import com.abhisu.abhisu_spring_boot_api.entity.User;
import com.abhisu.abhisu_spring_boot_api.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }
}
