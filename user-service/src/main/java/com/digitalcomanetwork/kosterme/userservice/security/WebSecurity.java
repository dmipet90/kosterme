package com.digitalcomanetwork.kosterme.userservice.security;

import com.digitalcomanetwork.kosterme.userservice.config.GatewayProperties;
import com.digitalcomanetwork.kosterme.userservice.config.LoginProperties;
import com.digitalcomanetwork.kosterme.userservice.config.TokenProperties;
import com.digitalcomanetwork.kosterme.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final GatewayProperties gatewayProperties;
    private final TokenProperties tokenProperties;
    private final LoginProperties loginProperties;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, tokenProperties, authenticationManager);
        authenticationFilter.setFilterProcessesUrl(loginProperties.getUrlPath());

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/user")
                        .access(new WebExpressionAuthorizationManager("hasIpAddress('"+ gatewayProperties.getIp() +"')"))
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())
                .addFilter(authenticationFilter)
                .authenticationManager(authenticationManager)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();

    }
 }
