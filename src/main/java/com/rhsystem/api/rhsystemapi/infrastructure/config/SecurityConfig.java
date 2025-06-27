package com.rhsystem.api.rhsystemapi.infrastructure.config;

import com.rhsystem.api.rhsystemapi.infrastructure.filters.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Define os caminhos públicos que não exigirão autenticação
    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
    };


    private final AuthenticationFilter authenticationFilter;
    private final AccessDeniedHandlerIml accessDeniedHandler;
    private final ApplicationAuthenticationEntryPoint applicationAuthenticationEntryPoint;


    public SecurityConfig(AuthenticationFilter authenticationFilter, AccessDeniedHandlerIml accessDeniedHandler, ApplicationAuthenticationEntryPoint applicationAuthenticationEntryPoint) {
        this.authenticationFilter = authenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.applicationAuthenticationEntryPoint = applicationAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> {
                ex.accessDeniedHandler(accessDeniedHandler);
                ex.authenticationEntryPoint(applicationAuthenticationEntryPoint);
            })
            .authorizeHttpRequests(
                    authorize -> authorize.requestMatchers(PUBLIC_ROUTES)
                                          .permitAll().anyRequest().authenticated())
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
