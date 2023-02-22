package com.khylo.reactivemongo.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.khylo.reactivemongo.repo.Reservation;

import reactor.core.publisher.Flux;
/*
 * See https://spring.io/guides/gs/reactive-rest-service/
 */
@Component
public class ReservationClient {
    public static void main(String[] args) {
        ReservationClient client = new ReservationClient(WebClient.builder());
        client.run();
    }

    public ReservationClient(WebClient.Builder builder) {
        this.wc = builder.baseUrl("http://localhost:8080").build();
      }

    private final WebClient wc;

    public void run() {
        
    }

    public Flux<Reservation> getReservations(){
        return this.wc
            .get()
            .uri("http://localhost:8080")
            .retrieve()
            .bodyToFlux(Reservation.class);
    }
}
