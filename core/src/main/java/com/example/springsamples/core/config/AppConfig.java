package com.example.springsamples.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class AppConfig {
  /** TODO: Find better alternative, For now just a workaround for exposing the public endpoint. */
  @Bean
  @Order(0)
  public SecurityFilterChain publicApiFilterChain(HttpSecurity http) {
    return http.securityMatcher("/api/public/**")
        .authorizeHttpRequests(
            request -> request.requestMatchers("/api/public/**").permitAll().anyRequest().denyAll())
        .csrf(AbstractHttpConfigurer::disable)
        .build();
  }
}
