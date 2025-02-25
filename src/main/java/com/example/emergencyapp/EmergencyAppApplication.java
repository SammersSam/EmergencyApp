package com.example.emergencyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmergencyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyAppApplication.class, args);
    }

}
