package com.smartautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartAutomationBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAutomationBeApplication.class, args);
    }
}
