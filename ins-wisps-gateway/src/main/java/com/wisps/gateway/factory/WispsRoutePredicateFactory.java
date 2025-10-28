package com.wisps.gateway.factory;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *自定义路由断言工厂
 */
@Component
public class WispsRoutePredicateFactory extends AbstractRoutePredicateFactory<WispsRoutePredicateFactory.Config> {

    public static final String PARAM_KEY = "param";

    public static final String REGEXP_KEY = "regexp";

    public WispsRoutePredicateFactory() {
        super(Config.class);
    }

    @Validated
    public static class Config{
        @NotEmpty
        private String param;
        @NotEmpty
        private String regexp;

        public String getParam() {
            return param;
        }

        public String getRegexp() {
            return regexp;
        }

        public Config setParam(String param) {
            this.param = param;
            return this;
        }

        public Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_KEY, REGEXP_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                ServerHttpRequest request = exchange.getRequest();
                String first = request.getQueryParams().getFirst(config.param);
                return StringUtils.hasText(first) && first.equals(config.regexp);
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Query: param=%s regexp=%s", config.getParam(), config.getRegexp());
            }
        };
    }
}
