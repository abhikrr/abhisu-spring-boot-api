package com.abhisu.abhisu_spring_boot_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Abhisu Kafka API")
                        .version("1.0")
                        .description("API documentation for Kafka messaging with Spring Boot 3.5.3"));
    }
}
