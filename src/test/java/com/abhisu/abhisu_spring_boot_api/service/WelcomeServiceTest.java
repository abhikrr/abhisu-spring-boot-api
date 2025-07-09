package com.abhisu.abhisu_spring_boot_api.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WelcomeServiceTest {

    private final WelcomeService service = new WelcomeService();

    @Test
    void testGetWelcomeMessage() {
        // Arrange
        WelcomeService welcomeService = new WelcomeService();

        // Act
        String result = welcomeService.getWelcomeMessage("Abhishek");

        // Assert
        assertEquals("Welcome Abhishek!", result);
    }

    @Test
    void testGetWelcomeMessage_withEmptyName() {
        String result = service.getWelcomeMessage("");
        assertEquals("Welcome !", result);
    }

    @Test
    void testGetWelcomeMessage_withNull() {
        String result = service.getWelcomeMessage(null);
        assertEquals("Welcome null!", result); // यह method null को भी string की तरह जोड़ता है
    }

    @Test
    void testGetWelcomeMessage_withWhitespace() {
        String result = service.getWelcomeMessage(" ");
        assertEquals("Welcome  !", result);
    }
}
