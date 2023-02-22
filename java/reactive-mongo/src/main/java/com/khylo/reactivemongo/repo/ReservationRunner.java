package com.khylo.reactivemongo.repo;

import java.util.function.Consumer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public record ReservationRunner(ReservationRepository repo) implements ApplicationRunner {

    @Override
    /**
     * Generate data
     */
    public void run(ApplicationArguments args) throws Exception {
        
        /*
        // Doing this the old fashioed way is like this

        var names = Flux.just("Sean", "Aisling", "Nessa", "Nancy", "Tom", "Dick", "Harry" );
        Flux<Reservation> reservations = names.map(name -> new Reservation(null,name));
        // We use flatmap next otherwise we have this
        //Flux<Mono<Reservation>> output = reservations.map(r -> this.repo().save(r));
        Flux<Reservation> output = reservations.flatMap(r -> this.repo().save(r));

        output.subscribe(new Consumer<Reservation>(){

            @Override
            public void accept(Reservation arg0) {
                log.info(arg0);
            }

        });
        */
        var names = Flux.just("Sean", "Aisling", "Nessa", "Nancy", "Tom", "Dick", "Harry" )
                    .map(name -> new Reservation(null,name))
                    .flatMap(r -> this.repo().save(r));
        // Now we have to create workflow to delete, data, then write to sb and log . 
        this.repo
            .deleteAll()
            .thenMany(names)
            .thenMany(this.repo.findAll())
            .subscribe(log::info);
        // For subscribe. Note how much simpler this is to lne 29 above.. 
        //names.subscribe(log::info); 
    }
    
}
