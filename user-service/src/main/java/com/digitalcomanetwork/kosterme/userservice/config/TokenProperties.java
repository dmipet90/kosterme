package com.digitalcomanetwork.kosterme.userservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Getter
@Setter
public class TokenProperties {
    private long expirationTime;
    private String secret;
}
