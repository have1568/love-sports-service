package com.love.sports.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * @author lovew
 */
@Configuration
public class GatewayClientFilter implements GlobalFilter, Ordered {

    @Resource
    private CustomValueProperties customValueProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //网关身份
        ServerHttpRequest newRequest = exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.setBasicAuth(customValueProperties.getClientId(),customValueProperties.getClientSecret())).build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
