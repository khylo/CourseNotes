package io.javabrains.reactiveworkshop;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.javabrains.reactiveworkshop.StreamSources.intNumbersStream;
import static io.javabrains.reactiveworkshop.StreamSources.userStream;

public class Exercise1 {

    public static void main(String[] args) {

        // Use StreamSources.intNumbersStream() and StreamSources.userStream()

        // Print all numbers in the intNumbersStream stream
        System.out.println("All number");
        intNumbersStream().forEach(System.out::println);

        // Print numbers from intNumbersStream that are less than 5
        System.out.println("All number less than 5");
        intNumbersStream().filter(s -> s < 5).forEach(System.out::println);

        // Print the second and third numbers in intNumbersStream that's greater than 5
        System.out.println("2nd and 3rd number greater than 5");
        intNumbersStream().filter(s -> s > 5).limit(3).skip(1).forEach(System.out::println);

        // Print 2nd and 3rd number less than 4
        System.out.println("2nd and 3rd number less than 4, ");
        intNumbersStream().filter(s -> s < 4).skip(1).limit(2).forEach(System.out::println);

        //  Print the first number in intNumbersStream that's greater than 5.
        //  If nothing is found, print -1
        System.out.println("1st number greater than 5, if none found print -1");
        intNumbersStream().filter(s -> s > 5).findFirst().ifPresentOrElse(System.out::println, () -> System.out.println("-1"));
        //Alternative from video https://youtu.be/cSJK67USyXA?list=PLqq-6Pq4lTTYPR2oH7kgElMYZhJd4vOGI&t=449
        Integer ans = intNumbersStream().filter(s -> s > 5).findFirst().orElse(-1);
        System.out.println(ans);

        //  Find 5, If nothing is found, print -1
        System.out.println("Find 5, if none found print -1");
        intNumbersStream().filter(s -> s == 5).findFirst().ifPresentOrElse(System.out::println, () -> System.out.println("-1"));

        // Split odd and even numbers
        System.out.println("Split odd and even");
        Map<Boolean, List<Integer>> resp = StreamSources.intNumbersStream().collect(Collectors.partitioningBy(s -> s % 2 == 0));
        System.out.println("Even = " + resp.get(Boolean.TRUE));
        System.out.println("Odd = " + resp.get(Boolean.FALSE));

        // Print first names of all users in userStream
        System.out.println("First names ");
        userStream().map(m -> m.getFirstName()).forEach(System.out::println);

        // Print first names in userStream for users that have IDs from number stream
        System.out.println("First names that share Ids ");
        List<Integer> indexes = intNumbersStream().collect(Collectors.toList());
        userStream().filter(us -> indexes.contains(us.getId())).map(us -> us.getFirstName()).forEach(System.out::println);
        // Other solution from video users flatmap
        intNumbersStream().flatMap(is -> userStream().filter(us -> us.getId() == is)).forEach(System.out::println);
        //Another solution is to use any
        userStream().filter(u -> intNumbersStream().anyMatch(i -> i == u.getId())).forEach(System.out::println);

    }

}
