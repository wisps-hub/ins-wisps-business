package com.wisps.gateway.factory;

import cn.hutool.core.lang.UUID;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义过滤器工厂
 */
@Component
public class OnceTokenGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    public static final String UUID_TOKEN = "uuid";
    public static final String JWT_TOKEN = "jwt";

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return chain.filter(exchange).then(Mono.fromRunnable(()->{
                    ServerHttpResponse response = exchange.getResponse();
                    HttpHeaders headers = response.getHeaders();
                    String tokenType = config.getValue();
                    if (StringUtils.hasText(tokenType)) {
                        tokenType = tokenType.toLowerCase();
                        if (tokenType.equals(UUID_TOKEN)) {
                            tokenType = UUID.fastUUID().toString();
                        }else if (tokenType.equals(JWT_TOKEN)){
                            tokenType = "jwt_123.345.567";
                        }
                        headers.add(config.getName(), tokenType);
                    }
                }));
            }
        };
    }

}
