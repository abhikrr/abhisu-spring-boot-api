package com.abhisu.abhisu_spring_boot_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Optional: use "USER" or "ADMIN"
}
