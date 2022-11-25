package com.khylo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Five5Letters 
{
    public static void main( String[] args ) throws IOException, URISyntaxException     {
        Five5Letters app = new Five5Letters();
        long start = System.nanoTime();
        app.run();
        long end = System.nanoTime();
        long time = end-start;
        System.out.println("Time taken = "+time+" ns ");
    }


    String words;
    List<List<String>> fiveLetterList=new ArrayList<List<String>>();

    public final void run() throws IOException, URISyntaxException{
        loadWords();
        analyseWordsTotal();

    }
    public final void analyseWordsTotal(){
        var letterDistribution = new HashMap<String,Integer>();
        var firstLetterDistribution = new HashMap<String,Integer>();
        var duoCombinations = new HashMap<String,Integer>();
        var wordArray = words.split("\n");
        System.out.println("Total number of words "+wordArray.length);
        for(var word: wordArray){
            int pos =0;
            for(var letter: word.split("")) {
                addTo(letterDistribution, letter);
                if (pos == 0)
                    addTo(firstLetterDistribution, letter);
                if(pos!=4)
                    addTo(duoCombinations, letter+word.indexOf(pos+1));
            }
        }
    }

    Map<String, Integer> addTo(Map<String, Integer> map,String val){
        if(map.keySet().contains(val)){
            map.put(val, map.get("val")+1);
        }
        map.put(val, Integer.valueOf(1));
    }

    public final void loadWords() throws IOException, URISyntaxException{
        this.words = loadWordsAllIn1();
    }
    private final String loadWordsAllIn1() throws IOException, URISyntaxException{
        //URL url = ClassLoader.getResource("/words");
        URL url = this.getClass().getClassLoader().getResource("words");
        return Files.readString(Path.of(url.toURI()));
    }


}
