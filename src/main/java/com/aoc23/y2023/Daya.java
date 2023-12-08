package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

public class Daya extends IDay{
    

    public Daya() {
        super("7");
    }


    public int solution1(List<String> input){


        return 0;
    }

    public int solution2(List<String> input){
       

     
        return 0;
    }

    public int getValue(String input)
    {
        Map<String,Integer> charCounts = new HashMap<String, Integer>();
        for(String eachChar: input.split("")){
            int count = charCounts.getOrDefault(charCounts, 0);
            charCounts.putIfAbsent(eachChar, count+1);
        }

        charCounts.keySet().stream()
        .max((String a, String b)-> 
            charCounts.get(a).compareTo(charCounts.get(b))
        );

        return 0;
    }
   
}
