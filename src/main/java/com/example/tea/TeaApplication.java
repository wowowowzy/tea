package com.example.tea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeaApplication.class, args);
    }

}
