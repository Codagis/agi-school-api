package com.codagis.agischool.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "escola.jwt")
public class JwtProperties {
    private String secretKey;
    private long validityInMs = 3600000;
    private long refreshValidityInMs = 86400000;
}