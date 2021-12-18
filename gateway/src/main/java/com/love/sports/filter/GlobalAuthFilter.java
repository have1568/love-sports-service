package com.love.sports.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lovew
 */
@Configuration
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAuthFilter.class);

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private CustomValueProperties customValueProperties;


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String currentUrl = exchange.getRequest().getPath().value();
        //白名单直接放行，不是白名单，获取 token token为空 返回错误   否则调用认证授权服务 验证token 验证通过，放行.否则响应未授权
        AtomicBoolean isWhitelist = new AtomicBoolean(false);
        Iterator<String> iterator = customValueProperties.getWhitelist().stream().iterator();
        while (iterator.hasNext()) {

            String next = iterator.next();
            if (antPathMatcher.match(next, currentUrl)) {
                isWhitelist.set(true);
                break;
            }
        }

        if (!isWhitelist.get()) {
            String token = getToken(exchange);
            //不是白名单，必须携带token,如果为空 返回未认证
            if (StringUtils.isEmpty(token)) {
                return unauthorized(exchange);
            }

            //token 不为空，调用认证服务器验证token 验证通过,放行
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(customValueProperties.getCheckTokenUrl()).queryParam("token", token);
            URI url = builder.build().encode().toUri();

            HttpEntity<?> entity = new HttpEntity<>(exchange.getRequest().getHeaders());
            ResponseEntity<CheckTokenRes> response = restTemplate().exchange(url, HttpMethod.GET, entity, CheckTokenRes.class);

            CheckTokenRes checkTokenRes = response.getBody();
            LOGGER.info("checkTokenRes:{}", checkTokenRes);

            if (checkTokenRes == null ||
                    !checkTokenRes.isActive() ||
                    StringUtils.hasText(checkTokenRes.getError()) ||
                    (checkTokenRes.getCode() != null && checkTokenRes.getCode() == 500)) {
                LOGGER.error("unauthorized : {}", checkTokenRes);
                return unauthorized(exchange);
            }

            for (String authority : checkTokenRes.getAuthorities()) {
                if (antPathMatcher.match(authority, currentUrl)) {
                    return chain.filter(exchange);
                }
            }
            return unauthorized(exchange);
        }
        return chain.filter(exchange);
    }

    private String getToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (!StringUtils.hasText(token)) {
            try {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                token = headers.getFirst("token");
                if (!StringUtils.hasText(token)) {
                    token = headers.getFirst("Authorization").split(" ")[1];

                }
            } catch (Exception e) {
                LOGGER.error("no token");
            }

        }

        return token;
    }


    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 2;
    }

}
