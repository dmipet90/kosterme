package com.digitalcomanetwork.kosterme.userservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "login")
@Getter @Setter
public class LoginProperties {
    private String urlPath;
}
