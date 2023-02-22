package com.khylo.reactivemongo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReservationRepository  extends ReactiveMongoRepository<Reservation, Integer>{
    
}
