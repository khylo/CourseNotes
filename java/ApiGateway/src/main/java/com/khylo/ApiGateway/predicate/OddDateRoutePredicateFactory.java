package com.khylo.ApiGateway.predicate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Predicate;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;

public class OddDateRoutePredicateFactory extends AbstractRoutePredicateFactory<OddDateRoutePredicateFactory.Config> {
    private static final Log log = LogFactory.getLog(PathRoutePredicateFactory.class);
    /**
     * DateTime key.
     */
    public static final String Odd_KEY = "OddVal";

    public OddDateRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(Odd_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                final ZonedDateTime now = ZonedDateTime.now();
                log.info("Checking OddDate Route Predicate with day = "+now.getDayOfMonth());
                return now.getDayOfMonth()%2==0;
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("After: %s", config.isOdd());
            }
        };
    }

    public static class Config {

        @NotNull
        private boolean odd;

        public boolean isOdd() {
            return odd;
        }

        public void setDatetime(boolean val) {
            this.odd=val;
        }

    }

}
