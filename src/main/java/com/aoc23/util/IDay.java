package com.aoc23.util;

import static com.aoc23.util.FileUtils.getFileLinesList;
import static com.aoc23.util.FileUtils.getFileString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class IDay {
    String day;

    public IDay(String day){
        this.day = day;
    }


    public String getFile(String fileName){
        String path = String.format("day%s\\%s", this.day, fileName);
        return getFileString(path);
    } 

    public List<String> getFileList(String fileName){
        String path = String.format("day%s\\%s", this.day, fileName);
        return getFileLinesList(path);
    } 

    public static Integer filterStringToIntegers(String input){
        return Integer.parseInt(input.replaceAll("[^0-9]", ""));
    }

    public int solution1(List<String> input){
        return 10;
    }
    public int solution2(List<String> input){
        return 0;
    }

    public List<Integer> splitToIntegersWithColon(String input, String splitter){
        List<String> splitString = Arrays.asList(input.trim().split(":")[1].trim().split(splitter));
        return splitString.stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList());
        
    }

    public List<Integer> splitToIntegers(String input, String splitter){
        List<String> splitString = Arrays.asList(input.trim().split(splitter));
        return splitString.stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList());
        
    }

    public List<Long> splitToLongs(String input, String splitter){
        List<String> splitString = Arrays.asList(input.trim().split(":")[1].trim().split(splitter));
        return splitString.stream().map(str -> Long.parseLong(str)).collect(Collectors.toList());
        
    }
}
