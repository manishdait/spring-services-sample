package com.example.springservicessample;

import org.springframework.boot.SpringApplication;

public class TestSpringServicesSampleApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringServicesSampleApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
