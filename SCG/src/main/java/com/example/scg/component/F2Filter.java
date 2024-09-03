package com.example.scg.component;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class F2Filter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("Pre global filter order : -2");

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    System.out.println("Post global filter order : -2");
                }));
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
