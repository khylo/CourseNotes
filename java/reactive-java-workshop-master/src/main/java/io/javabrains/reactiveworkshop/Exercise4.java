package io.javabrains.reactiveworkshop;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

public class Exercise4 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumberMono()

        // Print the value from intNumberMono when it emits
        ReactiveSources.intNumberMono().subscribe(i -> System.out.println(i));

        // Get the value from the Mono into an integer variable
        Optional<Integer> ans = ReactiveSources.intNumberMono().blockOptional(Duration.ofSeconds(5));

        //ALternative map
        //int mapAns = ReactiveSources.intNumberMono().map(i-> System.out.println(" .. received "+i)).

        System.out.println("Press a key to end");
        System.in.read();
        System.out.println("Ans = " + ans.orElse(-1));
    }

}
