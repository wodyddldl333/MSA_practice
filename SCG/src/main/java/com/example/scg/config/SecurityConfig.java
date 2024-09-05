//package com.example.scg.config;
//
//import com.example.scg.jwt.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//        return http
//                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
//                .formLogin(form -> form.disable())  // FormLogin 비활성화
//                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP 기본 인증 비활성화
//                .authorizeExchange(auth -> auth
//                        .pathMatchers("/login", "/join", "/reissue").permitAll()  // 인증이 필요 없는 경로
//                        .pathMatchers("/admin").hasRole("ADMIN")  // 관리자 권한 필요
//                        .anyExchange().authenticated()  // 나머지 경로는 인증 필요
//                )
//                .addFilterAt(jwtAuthenticationFilter, SecurityWebFilterChain.class)  // JWT 필터 추가
//                .build();
//    }
//}
