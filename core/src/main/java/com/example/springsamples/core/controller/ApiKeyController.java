package com.example.springsamples.core.controller;

import com.openelements.spring.base.services.apikey.ApiKeyCreateDto;
import com.openelements.spring.base.services.apikey.ApiKeyCreatedDto;
import com.openelements.spring.base.services.apikey.ApiKeyDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/api-key")
public class ApiKeyController {
  private final ApiKeyDataService apiKeyDataService;

  public ApiKeyController(ApiKeyDataService apiKeyDataService) {
    this.apiKeyDataService = apiKeyDataService;
  }

  /* Note: This will save the user of the access-token to the oe_spring_services.user table */
  @PostMapping
  public ResponseEntity<ApiKeyCreatedDto> createApiKey(@RequestBody ApiKeyCreateDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyDataService.create(request));
  }
}
