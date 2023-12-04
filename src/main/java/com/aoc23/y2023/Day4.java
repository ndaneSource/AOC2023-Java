package com.aoc23.y2023;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day4 extends IDay{
    
    public Map<String, Integer> cubeMap;

    public Day4() {
        super("4");
    }

    public int solution1(List<String> input){
        //foreach line, seperate by winning and chosen numbers |
        //count number of matches per card
        //get point count for card
        //sum wins together

        return input.parallelStream().map(card-> getCardValue(card))
            .reduce(Integer::sum).orElse(0);

    }

    public int solution2(List<String> input){
        return 0;
    }

    public int getCardValue(String card){
        String[] sides = card.split(":")[1].trim().split("\\|");

        List<String> winningNum = Arrays.asList(sides[0].trim().split(" "));
        List<String> guessedNum = Arrays.asList(sides[1].trim().split(" "));

        int winningNumbers = (int) winningNum.stream().filter(winNum-> guessedNum.contains(winNum))
        .count();
        System.out.println(winningNumbers);
        if(winningNumbers == 0)
            return 0;
        return (int) Math.pow(2, winningNumbers-1);
    }

}
