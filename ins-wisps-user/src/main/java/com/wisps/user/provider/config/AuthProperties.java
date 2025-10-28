package com.wisps.user.provider.config;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "wisps.auth")
public class AuthProperties {
    private String host;
    private String path;
    private String appcode;
}

