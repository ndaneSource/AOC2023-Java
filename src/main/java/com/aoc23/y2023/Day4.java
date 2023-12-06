package com.aoc23.y2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, Integer> cards = new HashMap<Integer, Integer>();
        for(int i =0; i < input.size();i++){
            int currentMultiplier =cards.getOrDefault(i,1);
            cards.put(i, currentMultiplier);
            int matches = getMatchCount(input.get(i));

            for(int j =1; j <=matches; j++){
                int nextCardMultiplier = cards.getOrDefault((i+j), 1);
                cards.put(i+j, currentMultiplier+nextCardMultiplier);
            }

            System.out.println(cards);

        }
        return cards.values().stream().reduce(Integer::sum).orElse(0);
    }

    public int getCardValue(String card){
        int winningNumbers = getMatchCount(card);
        if(winningNumbers == 0)
            return 0;
        return (int) Math.pow(2, winningNumbers-1);
    }

    public int getMatchCount(String card){
        String[] sides = card.split(":")[1].trim().split("\\|");

        List<String> winningNum = Arrays.asList(sides[0].trim().split("\\s+"));
        List<String> guessedNum = Arrays.asList(sides[1].trim().split("\\s+"));

        int winningNumbers = (int) winningNum.stream().filter(winNum-> guessedNum.contains(winNum))
        .count();

        return winningNumbers;
    }

}
