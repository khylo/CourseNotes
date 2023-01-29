package com.khylo.ApiGateway.predicate;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Forward to a n image representative of the weather
 * See https://open-meteo.com/en/docs
 * WMO Weather interpretation codes (WW)
 * Code	Description
 * 0	Clear sky
 * 1, 2, 3	Mainly clear, partly cloudy, and overcast
 * 45, 48	Fog and depositing rime fog
 * 51, 53, 55	Drizzle: Light, moderate, and dense intensity
 * 56, 57	Freezing Drizzle: Light and dense intensity
 * 61, 63, 65	Rain: Slight, moderate and heavy intensity
 * 66, 67	Freezing Rain: Light and heavy intensity
 * 71, 73, 75	Snow fall: Slight, moderate, and heavy intensity
 * 77	Snow grains
 * 80, 81, 82	Rain showers: Slight, moderate, and violent
 * 85, 86	Snow showers slight and heavy
 * 95 *	Thunderstorm: Slight or moderate
 * 96, 99 *	Thunderstorm with slight and heavy hail
 */
public class WeatherRoutePredicateFactory extends AbstractRoutePredicateFactory<WeatherRoutePredicateFactory.Config> {
    private static Map<String, String> weatherCode = Map.of("0", "Clear Sky",
                                            "1", "Mainly Clear Sky",
                                            "2", "Partly cloudy",
                                            "3", "Overcast",
                                            "45", "Fog",
                                            "51", "Light Drizzle",
                                            "53", "Moderate Drizzle",
                                            "55", "Dense Drizzle",
                                            "80", "Slight Rain",
                                            "81", "Moderate Rain");
    /**
     * DateTime key.
     */
    public static final String City_Key = "city";

    public WeatherRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(City_Key);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            /**
             * CAll out to weather service (with a timeout in case service has issues)
             * @param serverWebExchange the input argument
             * @return
             */
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .responseTimeout(Duration.ofMillis(5000))
                    .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

                WebClient client = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();
                String answer = client.get().exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    } else if (response.statusCode().is4xxClientError()) {
                        return Mono.just("Error response");
                    } else {
                        return response.createException()
                            .flatMap(Mono::error);
                    }
                }).block(Duration.ofSeconds(5));
                System.out.println("Response = "+answer);

                return true;
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Weather in: %s", config.getCity());
            }
        };
    }

    public static class Config {

        @NotNull
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

    }

}
