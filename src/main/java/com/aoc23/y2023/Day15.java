package com.aoc23.y2023;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day15 extends IDay{
    
    public Map<String, Integer> cubeMap;

    public Day15() {
        super("15");
    }

    public int solution1(List<String> input){
        List<String>splitInput = Arrays.asList(input.get(0).split(","));


        long result = splitInput.stream().map(segment-> hashAlg(segment)).reduce(Long::sum).orElse(0L);
        System.out.println(result);
        return 0;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public long hashAlg(String input){
        List<Long> split = Arrays.asList(input.split("")).stream().map(ch-> (long) ch.charAt(0)).collect(Collectors.toList());
        long sum =0;
        for(long in: split){
            sum += in;
            sum = sum*17;
            sum = sum%256;
        }
        
        
        return sum;
    }

}
