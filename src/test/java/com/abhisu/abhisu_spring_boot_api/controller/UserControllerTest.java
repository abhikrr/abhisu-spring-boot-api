//package com.abhisu.abhisu_spring_boot_api.controller;
//
//import com.abhisu.abhisu_spring_boot_api.entity.Role;
//import com.abhisu.abhisu_spring_boot_api.entity.User;
//import com.abhisu.abhisu_spring_boot_api.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(UserController.class)
//@Import(UserControllerTest.MockedServiceConfig.class)
//@AutoConfigureMockMvc(addFilters = false)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    @Configuration
//    static class MockedServiceConfig {
//        @Bean
//        public UserService userService() {
//            return Mockito.mock(UserService.class);
//        }
//    }
//
//    @Test
//    void testGetAllUsers() throws Exception {
//        when(userService.findAll()).thenReturn(List.of(
//                new User(1L, "Abhi", "abhi@mail.com", new Role())
//        ));
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testCreateUser() throws Exception {
//        User user = new User(null, "Abhi", "abhi@mail.com", new Role());
//        User savedUser = new User(1L, "Abhi", "abhi@mail.com", new Role());
//
//        when(userService.save(user)).thenReturn(savedUser);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isOk());
//    }
//}
