package io.javabrains.reactiveworkshop;

import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;

import java.io.IOException;

public class Exercise5 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumberMono() and ReactiveSources.userMono()

        // Subscribe to a flux using the error and completion hooks
        // Note an error is a final event, so no complete called
        Disposable subscription = ReactiveSources.intNumbersFlux().subscribe(
            item -> System.out.println(String.format("Item %d received.", item)),
            error -> System.out.println(String.format("Error %s received", error)),
            () -> System.out.println("Ints Complete")
        );

        ReactiveSources.userFlux().subscribe(
            item -> System.out.println(String.format("Item %s received.", item)),
            error -> System.out.println(String.format("Error %s received", error)),
            () -> System.out.println("User Complete")
        );

        // Subscribe to a flux using an implementation of BaseSubscriber
        ReactiveSources.userFlux().subscribe(new MySub<User>());

        System.out.println("Press a key to end");
        System.in.read();
    }

}

class MySub<T> extends BaseSubscriber<T> {
    public void hookOnSubscribe(Subscription sub) {
        System.out.println("Subscription happened");
        // Note must state request limit
        request(2);
    }

    public void hookOnNext(T item) {
        System.out.println("Next in Sub happened " + item.toString());
        request(2);
    }
}