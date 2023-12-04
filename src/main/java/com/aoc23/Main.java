package com.aoc23;

import java.util.List;

import com.aoc23.util.IDay;
import com.aoc23.y2023.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello AOC!");      
        
        runSolution(1,"testinput.txt");
        //runSolution(2,"input2.txt");

    }

    public static void runSolution(int part, String fileName){
        Day4 run = new Day4();
        List<String> input = run.getFileList(fileName);
        int solution = 0;
        if(part == 1)
            solution = run.solution1(input);
        else
            solution = run.solution2(input);

        System.out.println("output: "+solution);

    }
}