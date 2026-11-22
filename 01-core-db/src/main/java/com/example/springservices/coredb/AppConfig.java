package com.example.springservices.coredb;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration to add a filter before the spring-services security filter. to allow the
 * /api/v1/ticket/** endpoint without authentication.
 */
@Configuration
public class AppConfig {
  @Bean
  @Order(0)
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http.securityMatcher("/api/v1/ticket/**")
        .authorizeHttpRequests(request -> request.anyRequest().permitAll())
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

  @Bean
  public OpenAPI openAPIDefinition() {
    return new OpenAPI()
        .info(
            new Info()
                .title("TicketEntity Api")
                .description(
                    "An api demonstrating the use of the spring-services AbstractEntity, AbstractDbBackedServices and EntityRepository classes."));
  }
}
