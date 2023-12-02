package com.aoc23;

import java.util.ArrayList;
import java.util.List;

import com.aoc23.y2023.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello AOC!");      
        
        runSolution(1,"testinput.txt");
    }

    public static void runSolution(int part, String fileName){
         Day2 run = new Day2();
        List<String> input = run.getFileList(fileName);
        int solution = 0;
        if(part == 1)
            solution = run.solution1(input);
        else
            solution = run.solution2(input);

        System.out.println("output: "+solution);

    }
}