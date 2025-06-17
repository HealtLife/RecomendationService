package com.acme.nutrimove.recomendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RecomendationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecomendationServiceApplication.class, args);
    }
}