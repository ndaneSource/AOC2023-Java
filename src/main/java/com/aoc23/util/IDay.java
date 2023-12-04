package com.aoc23.util;

import static com.aoc23.util.FileUtils.getFileLinesList;
import static com.aoc23.util.FileUtils.getFileString;

import java.util.List;

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
}
