package com.khylo.reservationservice.model;

import lombok.Data;

@Data
public class Reservation {

    private String reservationName;

    public Reservation(String n){
        this.reservationName = n;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationName='" + reservationName + '\'' +
                '}';
    }
}
