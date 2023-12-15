package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aoc23.util.IDay;

public class Day13 extends IDay{
    
    public Map<String, Integer> cubeMap;
    public int smudgeLocation = -1;
    public List<String> puzzle = new ArrayList<String>();
    public String backupPuzzle = "";

    public Day13() {
        super("13");

    }

    //40427 too high
    //34326 too high
    public int solution1(List<String> input){
        int solution = 0;
        for(String line : input){
            if(line.trim().length() ==0 && puzzle.size() >0){
                solution += findReflection(puzzle);
                puzzle.clear();
            }else
                puzzle.add(line);
        }
        if(puzzle.size()>0)
            solution += findReflection(puzzle);

        return solution;
    }

    public int solution2(List<String> input){
        int solution = 0;
        for(String line : input){
            if(line.trim().length() ==0 && puzzle.size() >0){
                solution += findFuzzyReflection(puzzle);
                puzzle.clear();
                this.smudgeLocation = -1;
            }else
                puzzle.add(line);
        }
        if(puzzle.size()>0)
            solution += findFuzzyReflection(puzzle);

        return solution;
    }


    public int findFuzzyReflection(List<String> puzzle){

        for(int i = 0; i< puzzle.size()-1; i++){
            int compare = fuzzyCompareLines(puzzle.get(i), puzzle.get(i+1));
            if(compare >=0){
                smudgeLocation = compare;
                cleanPuzzle(i);
                int response = compareLines(i, i+1);
                if(response > 0)
                    return response;
                else
                    resetPuzzle(i);  
            }

            if(puzzle.get(i).equals(puzzle.get(i+1)))
                if(fuzzyConfirmPuzzleReflection(puzzle, i, i+1)){
                    return (i+1)*100;
                    
                }

            if(i< puzzle.size()-2){
                compare = fuzzyCompareLines(puzzle.get(i), puzzle.get(i+2));
                if(compare >=0){
                    smudgeLocation = compare;
                    cleanPuzzle(i);
                    int response = compareLines(i, i+2);
                    if(response > 0)
                        return response;
                    else
                        resetPuzzle(i);  
                }

                if(puzzle.get(i).equals(puzzle.get(i+2))){
                    if(fuzzyConfirmPuzzleReflection(puzzle, i, i+2)){
                        return (i+1)*100;
                    }
                }
            }
        }


        String line = puzzle.get(0);
        for(int i = 0; i< line.length()-1; i++){
            int compare = fuzzyCompareHorizontalLines(puzzle, i, i+1);
            if(compare >=0){
                smudgeLocation = compare;
                cleanPuzzleChar(i);
                int response = compareHorizontalLines(puzzle.get(0), i, i+1);
                if(response > 0)
                    return response;
                else
                    resetPuzzleChar();  
            }
            if(line.charAt(i) == line.charAt(i+1)){
                if(fuzzyConfirmLineReflection(puzzle, i, i+1)){
                    return (i+1);
                }
            }

            if(i< puzzle.size()-2){
                compare = fuzzyCompareHorizontalLines(puzzle, i, i+2);
                if(compare >=0){
                    smudgeLocation = compare;
                    cleanPuzzleChar(i);
                    int response = compareHorizontalLines(puzzle.get(0), i, i+2);
                    if(response > 0)
                        return response;
                    else
                        resetPuzzleChar();  
                }
                if(line.charAt(i) == line.charAt(i+1)){
                    if(fuzzyConfirmLineReflection(puzzle, i, i+2)){
                        return (i+1);
                    }
                }

            }
        }


        return 0;
    }

    public int fuzzyCompareLines(String top, String bottom){
        int difference = 0;
        int smudge = -1;
        for(int i =0; i < top.length(); i++){
            if(top.charAt(i) != bottom.charAt(i)){
                difference++;
                smudge = i;
            }
            if(difference > 1)
                return -2;
        }
        if(difference == 0)
            return -1;
        else
            return smudge;
    }

    public int fuzzyCompareHorizontalLines(List<String>puzzle, int left, int right){
        int difference = 0;
        int smudge = -1;
        for(int i =0; i < puzzle.size(); i++){
            String line = puzzle.get(i);
            if(line.charAt(left) != line.charAt(right)){
                difference++;
                smudge = i;
            }
            if(difference > 1)
                return -2;
        }
        if(difference == 0)
            return -1;
        else
            return smudge;
    }

    public int findReflection(List<String> puzzle){

        for(int i = 0; i< puzzle.size()-1; i++){
            if(puzzle.get(i).equals(puzzle.get(i+1))){
                if(confirmPuzzleReflection(puzzle, i, i+1)){
                    return (i+1)*100;
                }
            }
            if(i< puzzle.size()-2 && puzzle.get(i).equals(puzzle.get(i+2))){
                if(confirmPuzzleReflection(puzzle, i, i+2)){
                    return (i+1)*100;
                }
            }
        }
        
        String line = puzzle.get(0);
        for(int i = 0; i< line.length()-1; i++){
            if(line.charAt(i) == line.charAt(i+1)){
                if(confirmLineReflection(puzzle, i, i+1)){
                    return (i+1);
                }
            }
            if(i< puzzle.size()-2 && puzzle.get(i).equals(puzzle.get(i+2))){
                if(confirmLineReflection(puzzle, i, i+1)){
                    return (i+1);
                }
            }
        }

        return -1;
    }

    public int compareLines(int pos1, int pos2){
        if(puzzle.get(pos1).equals(puzzle.get(pos2))){
            if(confirmPuzzleReflection(puzzle, pos1, pos2)){
                return (pos1+1)*100;
            }
        }
        return -1;
    }

    public int compareHorizontalLines(String line, int pos1, int pos2){
        if(line.charAt(pos1) == line.charAt(pos2)){
                if(confirmLineReflection(puzzle, pos1, pos2)){
                    return (pos1+1);
                }
            }
        return -1;
    }

    public boolean confirmLineReflection(List<String> puzzle, int top, int bottom) {
        for(String line: puzzle){
            for(int i =0; top-i>=0 && bottom+i < line.length(); i++ )
                if(line.charAt(top-i) != line.charAt(bottom +i))
                    return false;
        }
        return true;
    }

    public boolean confirmPuzzleReflection(List<String> puzzle, int top, int bottom) {
        for(int i =0; top-i>=0 && bottom+i < puzzle.size(); i++ )
            if(!puzzle.get(top-i).equals(puzzle.get(bottom +i)))
                return false;
        return true;
    }

    public boolean fuzzyConfirmLineReflection(List<String> puzzle, int top, int bottom) {

        for(String line: puzzle){
            for(int i =0; top-i>=0 && bottom+i < line.length(); i++ ){
                if(line.charAt(top-i) != line.charAt(bottom +i)){
                    if(smudgeLocation == -1)
                        smudgeLocation =top-i;
                    else{
                        smudgeLocation = -1;
                        return false;
                    }
                }
            }
        }
        return smudgeLocation >=0;
    }

    public boolean fuzzyConfirmPuzzleReflection(List<String> puzzle, int top, int bottom) {
        for(int i =0; top-i>=0 && bottom+i < puzzle.size(); i++ ){
            int compare = fuzzyCompareLines(puzzle.get(top-i), puzzle.get(i+bottom));
            if(compare >=0 && smudgeLocation == -1){
                smudgeLocation = compare;
                continue;
            }
            else if(compare == -2|| (smudgeLocation>=0 && compare>=0)){
                smudgeLocation = -1;
                return false;
            }
        }
        return smudgeLocation >=0;
    }

    public void cleanPuzzle(int line){
        StringBuilder sb = new StringBuilder(puzzle.get(line));
        backupPuzzle = sb.toString();
        sb.replace(smudgeLocation, smudgeLocation+1, puzzle.get(line+1).substring(smudgeLocation, smudgeLocation+1));
        puzzle.set(line, sb.toString());
    }

    public void cleanPuzzleChar(int pos){
        StringBuilder sb = new StringBuilder(puzzle.get(smudgeLocation));
        backupPuzzle = sb.toString();
        sb.setCharAt(pos, sb.charAt(pos+1));
        puzzle.set(smudgeLocation, sb.toString());
    }

    public void resetPuzzle(int line){
        puzzle.set(line, backupPuzzle);
        smudgeLocation =-1;
    }

    public void resetPuzzleChar(){
        puzzle.set(smudgeLocation, backupPuzzle);
        smudgeLocation =-1;
    }
}
