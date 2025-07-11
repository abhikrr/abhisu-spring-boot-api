package com.abhisu.abhisu_spring_boot_api.service;

import static org.junit.jupiter.api.Assertions.*;

import com.abhisu.abhisu_spring_boot_api.entity.Role;
import com.abhisu.abhisu_spring_boot_api.entity.User;
import com.abhisu.abhisu_spring_boot_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        Role role = new Role(1L, "ROLE_USER");
        User user = new User(null, "Abhishek", "abhi@mail.com", role);
        when(userRepository.save(user)).thenReturn(new User(1L, "Abhishek", "abhi@mail.com", role));

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals("Abhishek", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindAllUsers() {
        Role role = new Role(1L, "ROLE_USER");
        List<User> users = Arrays.asList(
                new User(1L, "A", "a@mail.com", role),
                new User(2L, "B", "b@mail.com", role)
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}
