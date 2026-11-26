package com.example.springsamples.core.controller;

import com.example.springsamples.core.dao.DbHealthDao;
import com.openelements.spring.base.data.DbHealthService;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
  private final DbHealthService dbHealthService;

  public PublicController(@NonNull DbHealthService dbHealthService) {
    Objects.requireNonNull(dbHealthService, "dbHealthService must not be null");
    this.dbHealthService = dbHealthService;
  }

  @GetMapping("/db-health")
  public ResponseEntity<DbHealthDao> getDbHealth() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DbHealthDao(dbHealthService.isDatabaseReachable()));
  }
}
