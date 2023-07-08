package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(authorization -> {
            authorization
                    .requestMatchers("/products")
                    .permitAll();
            authorization
                    .requestMatchers("/products/create")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/products/{id}/edit")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/orders")
                    .hasRole("admin");
            authorization
                    .anyRequest()
                    .permitAll();
        });
        httpSecurity.formLogin();
        return httpSecurity.build();
    }
}
