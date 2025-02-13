package com.elev8.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,CustomCorsConfiguration customCorsConfiguration) throws Exception {
        http
                .cors(c->c.configurationSource(customCorsConfiguration))
                .csrf(c->c.disable()) // Disable CSRF for testing (optional)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
