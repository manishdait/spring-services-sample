package com.example.servicesample.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {
  /**
   * Configuration to add a filter before the spring-services core security filter. to allow the
   * /api/v1/public/** endpoint without authentication.
   */
  @Bean
  @Order(0)
  SecurityFilterChain publicFilterChain(HttpSecurity http) {
    return http.securityMatcher("/api/v1/public/**")
        .authorizeHttpRequests(request -> request.anyRequest().permitAll())
        .csrf(AbstractHttpConfigurer::disable)
        .build();
  }

  @Bean
  OpenAPI openAPIDefinition() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Spring Service Core Sample API")
                .description(
                    "An api demonstrating the use of the spring-services core classes and utilities."));
  }
}
