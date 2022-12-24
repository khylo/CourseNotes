package com.khylo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class Five5Letters {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Five5Letters app = new Five5Letters();
        long start = System.nanoTime();
        app.run();
        long end = System.nanoTime();
        long time = end - start;
        System.out.println("Time taken = " + time + " ns ");
    }


    String words;
    String[] wordArray;
    List<List<String>> fiveLetterList = new ArrayList<List<String>>();

    public final void run() throws IOException, URISyntaxException {
        loadWords();
        analyseWordsTotal();

    }

    public final void analyseWordsTotal() {
        var letterDistribution = new HashMap<String, Integer>();
        var firstLetterDistribution = new HashMap<String, Integer>();
        var duoCombinations = new HashMap<String, Integer>();
        wordArray = words.split("\n");
        System.out.println("Total number of words " + wordArray.length);
        for (var word : wordArray) {
            int pos = 0;
            for(int i=0;i<5;i++){
                var letter = word.substring(i,i+1);
                addTo(letterDistribution, letter);
                if (i == 0)
                    addTo(firstLetterDistribution, letter);
                if (i < 4)
                    addTo(duoCombinations, word.substring(i,i+2));
            }
        }
        System.out.println("Letter Distributions " + mapToString(letterDistribution));
        System.out.println("Letter Distributions " + mapToString(sortByValue(letterDistribution)));
        System.out.println("1st Letter Distributions " + mapToString(sortByValue(firstLetterDistribution)));
        System.out.println("Due Letter Distributions " + mapToString(sortByValue(duoCombinations)));
    }

    private void addTo(Map<String, Integer> map, String val) {
        if (map.keySet().contains(val)) {
            var current = map.get(val);
            map.put(val, current + 1);
            return;
        }
        map.put(val, Integer.valueOf(1));
        return ;
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> map) {
        return map.entrySet().stream()
            .sorted(
                Collections.reverseOrder(
                    Map.Entry.comparingByValue()
                )
            ).collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue
                )
            );

    }

    private String mapToString(Map<String, Integer> map) {
        return map.entrySet().stream()
            .flatMap(
                entry -> Stream.of(String.format("[%s:%d]", entry.getKey(), entry.getValue()))
            ).collect(Collectors.joining(","));
    }

    public final void loadWords() throws IOException, URISyntaxException {
        this.words = loadWordsAllIn1();
    }

    private final String loadWordsAllIn1() throws IOException, URISyntaxException {
        //URL url = ClassLoader.getResource("/words");
        URL url = this.getClass().getClassLoader().getResource("words");
        return Files.readString(Path.of(url.toURI()));
    }


}
