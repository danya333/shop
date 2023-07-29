package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
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
                    .requestMatchers("/orders/{orderId}/change")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/orders/{id}")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/products/{id}/delete")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/products/{productId}/edit/review/{reviewId}/confirm")
                    .hasRole("admin");
            authorization
                    .requestMatchers("/products/{id}/edit/review/add")
                    .hasRole("user");
            authorization
                    .anyRequest()
                    .permitAll();
        });
        httpSecurity.formLogin();
        return httpSecurity.build();
    }
}
