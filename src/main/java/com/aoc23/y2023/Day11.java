package com.aoc23.y2023;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

public class Day11 extends IDay{
    
    public Map<Integer,Pair<Integer,Integer>> galaxyMap;
    public Set<Integer> galRows;
    public Set<Integer> galColumns;

    public Day11() {
        super("11");
        galaxyMap = new HashMap();
        galRows = new HashSet();
        galColumns = new HashSet();
    }

    public int solution1(List<String> input){
        findGalaxies(input);
        moveGalaxies();
        return travelGalaxies();
        
    }

    public int solution2(List<String> input){
        return 0;
    }

    public void findGalaxies(List<String>input){
        
        for(int i =0; i< input.size(); i++){
            int index = input.get(i).indexOf("#");
            while(index >=0){
                galaxyMap.put(galaxyMap.size(),new Pair<Integer,Integer>(i, index));
                galRows.add(i);
                galColumns.add(index);
                index = input.get(i).indexOf("#", index+1);
            }
        }
        galColumns = galColumns.stream().sorted().collect(Collectors.toSet());
    }

    public void moveGalaxies(){
        int multiplier = 1000000-1;
        for(int i = 0; i< galaxyMap.size(); i++){
            Pair<Integer,Integer> p = galaxyMap.get(i);
            int line = p.getValue0();
            int pos = p.getValue1();
            int increaseCol = (int) galColumns.stream().filter(col -> col < pos).count();
            //increaseCol++;
            increaseCol = (pos - increaseCol)*multiplier;
            int increaseRow = (int) galRows.stream().filter(row -> row < line).count();
            //increaseRow++;
            increaseRow = (line - increaseRow)*multiplier;

            galaxyMap.replace(i, new Pair(line+increaseRow, pos + increaseCol));
        }
    }

    public int travelGalaxies(){
        long distance = 0;

        for(int i = 0; i < galaxyMap.size(); i++){
            for(int j = i+1; j< galaxyMap.size(); j++){
                Pair<Integer,Integer> galA = galaxyMap.get(i);
                Pair<Integer,Integer>  galB = galaxyMap.get(j);
                int dist = Math.abs(galA.getValue0() - galB.getValue0())
                    +Math.abs(galA.getValue1() - galB.getValue1());
                distance+=dist;
                //System.out.println(i+" "+j+" "+dist+" "+galA.toString()+" "+galB.toString());
            }
        }

        System.out.println("solution "+distance);
        return 0;
    }


}
