package com.khylo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class Five5LettersTest {
    @Test
    public void test(){
        System.out.println("Running test");
        Assertions.assertTrue(1==1);
    }

    @Test
    public void testSort(){
        Map <String, Integer> map = Map.of("a",1,"b",2,"c",5,"d",4);
        System.out.println(mapToString(map));
        System.out.println(mapToString(sort(map)));
    }

    private String mapToString(Map map){
        StringBuilder ret = new StringBuilder("[");
        map.forEach((k,v)-> ret.append("["+k+","+v+"],"));
        return ret.append("]").toString();
    }

    private Map sort(Map<String, Integer> map){
        Map ret = new LinkedHashMap(map.size());
        return map.entrySet().stream().sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())).collect((k,v) -> ret.put(k,v));
    }

    private String mapToStringOther(Map<String, Integer> map) {
        return map.entrySet().stream()
            .flatMap(
                entry -> Stream.of(String.format("[%s:%d]", entry.getKey(), entry.getValue()))
            ).collect(Collectors.joining(","));
    }
}
