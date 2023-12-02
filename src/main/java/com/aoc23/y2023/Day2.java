package com.aoc23.y2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.aoc23.util.IDay;

public class Day2 extends IDay{
    
    public Map<String, Integer> cubeMap;

    public Day2() {
        super("2");
        populateCubeMap();
    }

   

    public int solution1(List<String> input){
        //12 red cubes, 13 green cubes, and 14 blue cubes
        return input.parallelStream().filter(line ->
            filterCubes(line)
        ).map(Day2::getGameNumber).reduce(Integer::sum).orElse(0);        
    }

    public int solution2(List<String> input){
        return input.parallelStream().map(line ->
            assessCubes(line)
        ).reduce(Integer::sum).orElse(0);   
    }

    public Boolean filterCubes(String line) {
        String[] cubeParts = line.split(":")[1].split("[,;]");
        for(String cubePart : cubeParts){
            var cubes = cubePart.trim().split(" ");
            int cubeCount = Integer.parseInt(cubes[0]);
            if(cubeCount> cubeMap.get(cubes[1]))
                return false;
        }
        return true;
    }

    public int assessCubes(String line) {
        Map<String, Integer> minColors = new HashMap<String, Integer>();
        String[] cubeParts = line.split(":")[1].split("[,;]");

        for(String cubePart: cubeParts){
            var cubes = cubePart.trim().split(" ");
            var max = Math.max(minColors.getOrDefault(cubes[1], 0), Integer.parseInt(cubes[0]));
            minColors.put(cubes[1], max);
        }

        return minColors.values().stream().reduce(1, (a, b)-> a*b);
    }
 

    public static int getGameNumber(String line){
        return filterStringToIntegers(line.split(":")[0]);
    }

    private void populateCubeMap() {
        cubeMap = Map.of("red", 12,
            "green", 13,
            "blue", 14);
    }


}
