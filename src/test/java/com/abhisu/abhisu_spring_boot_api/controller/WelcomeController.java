package com.abhisu.abhisu_spring_boot_api.controller;

import com.abhisu.abhisu_spring_boot_api.service.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final WelcomeService welcomeService;

    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return welcomeService.getWelcomeMessage(name);
    }
}
