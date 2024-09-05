//package com.example.scg.jwt;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.http.HttpStatus;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class JwtAuthenticationLFilter extends AbstractGatewayFilterFactory<JwtAuthenticationLFilter.Config> {
//
//    private final JWTUtil jwtUtil;
//
//    public JwtAuthenticationLFilter(JWTUtil jwtUtil) {
//        super(Config.class);
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            String token = getTokenFromRequest(request);
//
////            System.out.println(token);
//
//            if (token == null) {
//
//                return handleUnauthorized(exchange, "JWT Token is missing");
//            }
//
//            try {
//                jwtUtil.isTokenExpired(token);
////                jwtUtil.validateToken(token);
//            } catch (ExpiredJwtException e) {
//                // 만료된 토큰
//                return handleUnauthorized(exchange, "JWT Token is expired");
//            } catch (JwtException | IllegalArgumentException e) {
//                // 기타 유효하지 않은 토큰
//                String access = jwtUtil.createToken("access", "admin", "ADMIN", 600000L);
//                return handleUnauthorized(exchange, access);
//            }
//
//            String username = jwtUtil.getUsername(token);
//            ServerHttpRequest modifiedRequest = request.mutate()
//                    .header("X-auth-username", username).build();
//
//            return chain.filter(exchange.mutate().request(modifiedRequest).build());
//        });
//    }
//
//    private String getTokenFromRequest(ServerHttpRequest request) {
//
//        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            return authorizationHeader.substring(7);
//        }
//        return null;
//    }
//
//    private Mono<Void> handleUnauthorized(ServerWebExchange exchange, String message) {
//
//        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(HttpStatus.SC_UNAUTHORIZED));
//        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
//
//        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
//        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
//    }
//
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Data
//    public static class Config {
//        private boolean pre;
//        private boolean post;
//    }
//}
