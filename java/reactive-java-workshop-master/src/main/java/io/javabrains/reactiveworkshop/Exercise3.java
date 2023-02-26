package io.javabrains.reactiveworkshop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Exercise3 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux()

        // Get all numbers in the ReactiveSources.intNumbersFlux stream
        // into a List and print the list and its size
        List<Integer> l = new ArrayList<>();
        ReactiveSources.intNumbersFlux().subscribe(s -> l.add(s));

        //Other solution
        // BUt NOTE, this is blocking on toList
        Stream s = ReactiveSources.intNumbersFlux().toStream();
        System.out.println("AFter toStream");
        List<Integer> l2 = s.toList();

        System.out.println("Press a key to end");
        System.out.println(String.format("List length before=%d" + l, l.size()));
        System.in.read();

        System.out.println(String.format("List length after=%d" + l, l.size()));
    }

}
