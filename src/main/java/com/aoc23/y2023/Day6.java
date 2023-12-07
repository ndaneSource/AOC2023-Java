package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

public class Day6 extends IDay{
    

    public Day6() {
        super("6");
    }


    public int solution1(List<String> input){
        List<Long> times = splitToLongs(input.get(0), "\\s+");
        List<Long> distances = splitToLongs(input.get(1), "\\s+");

        long raceResults = 1;

        for(int i =0; i< times.size(); i++){
            final int r = i;
            long completions = (Long) LongStream.range(0, times.get(i)).boxed().filter(race -> canFinish(race, times.get(r), distances.get(r))).count();
            raceResults = raceResults * completions;
        }
        System.out.println("output: "+raceResults);

        return 0;
    }

    public int solution2(List<String> input){
       

     
        return 0;
    }


    public boolean canFinish(long charge, long time, long distance){
        return distance < (time - charge) * charge;
    }

   
}
