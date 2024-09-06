package com.example.scg.jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

// 로그인과 회원가입 시에는 jwt가 없기 때문에 예외 처리
@Component
public class RouteValidator {

    public static final List<String> openApiEndPoints = List.of(
            "/eureka",
            "/ms1/login",
            "/ms1/join"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
