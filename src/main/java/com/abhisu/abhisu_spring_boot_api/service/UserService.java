package com.abhisu.abhisu_spring_boot_api.service;

import com.abhisu.abhisu_spring_boot_api.entity.User;
import com.abhisu.abhisu_spring_boot_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
