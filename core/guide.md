# Usage Guide

## Setup

### 1. Maven Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>com.open-elements</groupId>
        <artifactId>spring-services-bom</artifactId>
        <version>${spring.services.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
    
    <dependency>
        <groupId>com.open-elements</groupId>
        <artifactId>spring-services-core</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    </dependency>
</dependencies>
```

Core already brings `spring-boot-starter-webmvc`, `-security`, `-data-jpa`, `-validation` and `-security-oauth2-resource-server`.

### 2. Application Properties

```yml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/service_db
    username: postgres
    password: password

  jpa:
    database: postgresql
    hibernate:
      ddl-auto: update
    properties:
      jakarta:
        persistence:
          create-database-schemas: true

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/services_provider
```