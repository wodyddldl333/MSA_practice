//package com.example.scg.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteConfig {
//
//    @Bean
//    public RouteLocator ms1Route(RouteLocatorBuilder builder) {
//
//        return builder.routes()
//                .route("ms1", r -> r.path("/ms1/**")
//                        .uri("http://localhost:8081"))
//                .route("ms2", r -> r.path("/ms2/**")
//                        .uri("http://localhost:8082"))
//                .build();
//    }
//}