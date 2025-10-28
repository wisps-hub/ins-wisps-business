package com.wisps.gateway.filter;

import com.wisps.gateway.consts.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器
 */
@Slf4j
@Component
public class DefaultGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String uri = request.getURI().toString();
        long start = System.currentTimeMillis();
        log.info("请求 uri: {}, 开始时间: {}", uri, start);
        //上面是前值逻辑

        Mono<Void> mono = chain.filter(exchange).doFinally(signalType -> {
            long end = System.currentTimeMillis();
            log.info("请求 uri: {}, 结束时间: {}, 耗时: {}", uri, end, end - start);
        });

        //下面是后置逻辑
        log.info("请求 uri: {}, 响应状态码: {}", uri, response.getStatusCode());

        return mono;
    }

    @Override
    public int getOrder() {
        return Consts.DEFAULT_FILTER_ORDER;
    }
}
