package com.wisps.ai.porovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class WispsAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WispsAiApplication.class, args);
        log.info("===============================");
        log.info("server started");
        log.info("===============================");
    }
}