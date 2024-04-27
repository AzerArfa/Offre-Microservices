package com.ecom.filter;

import com.ecom.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    if (Objects.equals(jwtUtil.extractAllClaims(authHeader).toString(), "ADMIN")) {
                        if (!(exchange.getRequest().getPath().toString().startsWith("/offer/admin"))) {
                            throw new Exception("un authorized access to application");
                        }
                    } else if (Objects.equals(jwtUtil.extractAllClaims(authHeader).toString(), "USER")) {
                        if (!(exchange.getRequest().getPath().toString().startsWith("/offer/user"))) {
                            throw new Exception("un authorized access to application");
                        }
                    } else if (Objects.equals(jwtUtil.extractAllClaims(authHeader).toString(), "SUPERADMIN")) {
                        if (!(exchange.getRequest().getPath().toString().startsWith("/superadmin"))) {
                            throw new Exception("un authorized access to application");
                        }
                    }
                } catch (Exception e) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
            } else {
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
