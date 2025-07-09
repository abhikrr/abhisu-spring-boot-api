package com.abhisu.abhisu_spring_boot_api.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloControllerTest {

    @Test
    void testSayHello() {
        HelloController controller = new HelloController();
        String response = controller.sayHello();
        assertEquals("Hello Abhishek!", response);
    }
}
