package com.wisps.user.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class WispsUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(WispsUserApplication.class, args);
        log.info("===============================");
        log.info("server started");
        log.info("===============================");
    }
}