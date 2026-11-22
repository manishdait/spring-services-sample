package com.example.springservices.coredb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration to add a filter before the spring-services security filter. to allow the
 * /api/v1/public/** endpoint without authentication.
 */
@Configuration
public class SecurityConfig {
  @Bean
  @Order(0)
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http.securityMatcher("/api/v1/public/**")
        .authorizeHttpRequests(request -> request.anyRequest().permitAll())
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
