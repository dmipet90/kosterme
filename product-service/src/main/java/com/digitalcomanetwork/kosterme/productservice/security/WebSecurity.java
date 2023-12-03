package com.digitalcomanetwork.kosterme.productservice.security;

import com.digitalcomanetwork.kosterme.productservice.config.GatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final GatewayProperties gatewayProperties;
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/product/create")
                        .access(new WebExpressionAuthorizationManager("hasIpAddress('"+ gatewayProperties.getIp() +"')"))
                .requestMatchers(HttpMethod.GET, "/product/status/check")
                        .access(new WebExpressionAuthorizationManager("hasIpAddress('"+ gatewayProperties.getIp() +"')"))
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();

    }
 }
