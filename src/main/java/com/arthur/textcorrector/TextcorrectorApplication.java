package com.arthur.textcorrector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TextcorrectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextcorrectorApplication.class, args);
    }

}
