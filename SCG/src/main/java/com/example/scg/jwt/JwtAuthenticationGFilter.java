package com.example.scg.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationGFilter implements GlobalFilter, Ordered {

    private final JWTUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 특정 경로에 대해 예외 처리 (예: "/public", "/login" 등)
        String path = request.getURI().getPath();
        if (path.startsWith("/ms1/login") || path.startsWith("/ms1/join")) {
            return chain.filter(exchange); // 예외 처리한 경로는 필터를 통과시킴
        }

        String token = getTokenFromRequest(request);

        if (token == null) {
            return handleUnauthorized(exchange, "JWT Token is missing");
        }

        try {
            jwtUtil.isTokenExpired(token); // 토큰 만료 체크
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            return handleUnauthorized(exchange, "JWT Token is expired");
        } catch (JwtException | IllegalArgumentException e) {
            // 기타 유효하지 않은 토큰
            // 2줄 테스트 코드
            String access = jwtUtil.createToken("access", "admin", "ADMIN", 600000L);
            return handleUnauthorized(exchange, access);
//            return handleUnauthorized(exchange, "Invalid JWT Token");
        }

        // 토큰이 유효할 경우, 사용자 정보를 헤더에 추가
        String username = jwtUtil.getUsername(token);
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-auth-username", username)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(HttpStatus.SC_UNAUTHORIZED));
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

    // 필터의 우선 순위 설정 (숫자가 낮을수록 먼저 실행)
    @Override
    public int getOrder() {
        return -1;
    }
}
