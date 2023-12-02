package com.aoc23.y2023;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day1 extends IDay{
    
    public Map<String, String> numMap;

    public Day1() {
        super("1");
        populateNumMap();
    }

   

    public int solution1(List<String> input){
        List<Integer> numbers = fillterStringToTwoIntegers(input);

        int sum = numbers.stream().reduce(0, Integer::sum);

        return sum;
    }

    public int solution2(List<String> input){
        List<String> convertedString = input.parallelStream()
            .map(line-> convertNumString(line))
            .toList();

        System.out.println(convertedString);
        return solution1(convertedString);
    }

    public String convertNumString(String line) {
        String originalString = "";
        for(int i = 0; i < line.length(); i++){
            if(numMap.values().contains(""+line.charAt(i)))
                break;
            
            originalString += line.charAt(i);

            String changed = convertNumSubString(originalString);

            if (!originalString.equals(changed)){
                line = changed+line.substring(i+1);
                break;
            }
        }

        originalString = "";
        for(int i = line.length()-1; i > 0; i--){
            if(numMap.values().contains(""+line.charAt(i)))
                break;

            originalString = line.charAt(i)+originalString;

            String changed = convertNumSubString(originalString);

            if (!originalString.equals(changed) ){
                line = line+changed;
                break;
            }
        }
        return line;
    }



    public String convertNumSubString(String input) {
        List<String> hits = numMap.keySet().parallelStream()
            .filter(key -> input.contains(key))
            .collect(Collectors.toList());
        return hits.size() > 0 ? numMap.get(hits.get(0)): input;
   
    }

    private List<Integer> fillterStringToTwoIntegers(List<String> input){
        return input.parallelStream().map(line -> line
            .replaceAll("[^0-9]", ""))
            .map(line -> line.substring(0,1)+line.charAt(line.length()-1))
            .map(Integer::parseInt).collect(Collectors.toList());
    }

     private void populateNumMap() {
        numMap = Map.of("one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9");
    }


}
