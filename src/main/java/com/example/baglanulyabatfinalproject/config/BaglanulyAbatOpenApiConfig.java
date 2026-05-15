package com.example.baglanulyabatfinalproject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "BaglanulyAbatJWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class BaglanulyAbatOpenApiConfig {

    @Bean
    public OpenAPI baglanulyAbatOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BaglanulyAbat Task Management System API")
                        .description("REST API для управления задачами, проектами и пользователями")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BaglanulyAbat")
                                .email("baglanulyabat@example.com"))
                        .license(new License().name("MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development")
                ));
    }
}