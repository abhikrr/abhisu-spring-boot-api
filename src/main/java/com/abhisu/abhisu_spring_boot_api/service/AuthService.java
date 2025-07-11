package com.abhisu.abhisu_spring_boot_api.service;

import com.abhisu.abhisu_spring_boot_api.dto.*;
import com.abhisu.abhisu_spring_boot_api.entity.Role;
import com.abhisu.abhisu_spring_boot_api.entity.User;
import com.abhisu.abhisu_spring_boot_api.repository.RoleRepository;
import com.abhisu.abhisu_spring_boot_api.repository.UserRepository;
import com.abhisu.abhisu_spring_boot_api.security.CustomUserDetails;
import com.abhisu.abhisu_spring_boot_api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName("ROLE_" + request.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getUsername(), role.getName());
        return new AuthResponse(token);
    }
}
