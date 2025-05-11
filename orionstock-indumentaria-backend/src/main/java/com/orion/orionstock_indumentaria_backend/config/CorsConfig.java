package com.orion.orionstock_indumentaria_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Permitir todos los orígenes
                .allowedMethods("*") // Permitir todos los métodos (incluye OPTIONS)
                .allowedHeaders("*") // Permitir todos los headers
                .allowCredentials(false); // Cambia a true si usas autenticación
    }
}
