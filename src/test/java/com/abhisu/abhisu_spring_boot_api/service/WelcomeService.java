package com.abhisu.abhisu_spring_boot_api.service;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {
    public String getWelcomeMessage(String name) {
        return "Welcome " + name + "!";
    }
}
