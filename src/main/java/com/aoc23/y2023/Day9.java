package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day9 extends IDay{
    
    public Map<String, Integer> cubeMap;

    public Day9() {
        super("9");
    }

    public int solution1(List<String> input){
        return input.stream().map(line-> findNextValue(line)).reduce(Integer::sum).orElse(0);

    }

    public int solution2(List<String> input){
        return input.stream().map(line-> findPrevValue(line)).reduce(Integer::sum).orElse(0);
    }

    public int findNextValue(String line){
        String[] sequenceString = line.trim().split("\\s");

        int[] seq = Arrays.stream(sequenceString).mapToInt(Integer::parseInt).toArray();
        return this.nextInSeries(Arrays.stream(seq).boxed().collect(Collectors.toList()));
    }

    public int nextInSeries(List<Integer> seq){
        ArrayList<Integer> nextSeq = new ArrayList<Integer>();
        for(int i =1; i < seq.size(); i++){
            nextSeq.add(seq.get(i)-seq.get(i-1));
        }
            if(nextSeq.getLast() == 0)
                return seq.getLast();
            else
                return nextInSeries(nextSeq)+seq.getLast();
    }


    public int findPrevValue(String line){
        String[] sequenceString = line.trim().split("\\s");

        int[] seq = Arrays.stream(sequenceString).mapToInt(Integer::parseInt).toArray();
        int value = this.prevInSeries(Arrays.stream(seq).boxed().collect(Collectors.toList()));

        System.out.println("line "+value);
        return value;
    }

    public int prevInSeries(List<Integer> seq){
        ArrayList<Integer> nextSeq = new ArrayList<Integer>();
        for(int i =1; i < seq.size(); i++){
            nextSeq.add(seq.get(i)-seq.get(i-1));
        }
            if(nextSeq.getLast() == 0)
                return seq.getFirst();
            else
                return seq.getFirst()-prevInSeries(nextSeq);
    }

}
