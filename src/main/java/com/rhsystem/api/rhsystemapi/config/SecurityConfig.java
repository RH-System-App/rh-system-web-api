package com.rhsystem.api.rhsystemapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Define os caminhos públicos que não exigirão autenticação
    private static final String[] PUBLIC_ROUTES = {
            // --- Rotas de Autenticação ---
            "/**", // Libera todos os endpoints sob /api/auth/ (ex: login, register, refresh-token)
            // /api/auth/*
            // --- Rotas do Swagger/OpenAPI ---
            "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                authorize -> authorize.requestMatchers(PUBLIC_ROUTES).permitAll().anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
