package com.abhisu.abhisu_spring_boot_api.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.abhisu.abhisu_spring_boot_api.service.WelcomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WelcomeControllerTest {

    @Mock
    private WelcomeService mockWelcomeService;

    @InjectMocks
    private WelcomeController welcomeController;

    @Test
    void testWelcome() {
        // Arrange
        when(mockWelcomeService.getWelcomeMessage("Abhishek")).thenReturn("Welcome Abhishek!");

        // Act
        String result = welcomeController.welcome("Abhishek");

        // Assert
        assertEquals("Welcome Abhishek!", result);
        verify(mockWelcomeService, times(1)).getWelcomeMessage("Abhishek");
    }
}
