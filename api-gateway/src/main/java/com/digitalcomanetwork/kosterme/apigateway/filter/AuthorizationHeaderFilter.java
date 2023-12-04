package com.digitalcomanetwork.kosterme.apigateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.digitalcomanetwork.kosterme.apigateway.config.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final TokenProperties tokenProperties;

    public AuthorizationHeaderFilter(TokenProperties tokenProperties) {
        super(Config.class);
        this.tokenProperties = tokenProperties;
    }

    public static class Config {
        private List<String> authorities;

        public List<String> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(String authorities) {
            this.authorities = Arrays.asList(authorities.split(" "));
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("authorities");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String jwt = authorizationHeader.replace("Bearer", "").trim();

            List<String> authorities = getAuthorities(jwt);

            boolean hasRequieredAuthority = authorities.stream()
                    .anyMatch(authority -> config.getAuthorities().contains(authority));
            if (!hasRequieredAuthority) {
                return onError(exchange, "User is not authorized to perform this operation", HttpStatus.FORBIDDEN);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatusCode status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer buffer = bufferFactory.wrap(err.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    private List<String> getAuthorities(String jwt) {
        List<String> returnValue = new ArrayList<>();
        String tokenSecret = tokenProperties.getSecret();
        SecretKey signingKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        JwtParser jwtParser = Jwts.parser().verifyWith(signingKey).build();
        try {
            Jws<Claims> parsedToken = jwtParser.parseSignedClaims(jwt);
            Collection<Map<String, String>> scopes = parsedToken.getPayload().get("scope", List.class);
            returnValue = scopes.stream().map(scopeMap -> scopeMap.get("authority")).toList();
        } catch (Exception ex) {
            return returnValue;
        }
        return returnValue;
    }
}
