package com.digitalcomanetwork.kosterme.userservice.security;

import com.digitalcomanetwork.kosterme.userservice.config.TokenProperties;
import com.digitalcomanetwork.kosterme.userservice.service.UserService;
import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import com.digitalcomanetwork.kosterme.userservice.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final TokenProperties tokenProperties;

    public AuthenticationFilter(UserService userService,
                                TokenProperties tokenProperties,
                                AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.userService = userService;
        this.tokenProperties = tokenProperties;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    ));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res, FilterChain chain,
                                            Authentication auth) {
        String userName = ((User)auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(userName);
        String tokenSecret = tokenProperties.getSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();
        String token = Jwts.builder()
                .claim("scope", auth.getAuthorities())
                .subject(userDetails.getUserId())
                .expiration(Date.from(now.
                        plusMillis(tokenProperties.getExpirationTime())))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }
}
