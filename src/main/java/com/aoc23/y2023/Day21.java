package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day21 extends IDay{
    
    public List<String> gardenMap;
    public int lineNumberMax;
    public int linePosMax;
    public List<PuzzleHelper> steps;
    public Set<String> currentSteps;

    public Day21() {
        super("21");
    }

    public int solution1(List<String> input){
        lineNumberMax = input.size();
        linePosMax = input.get(0).length();
        int stopStep = 26501365;
        long exp = 1;

        gardenMap = input;
        int[] start = findS(input);

        steps = new ArrayList<PuzzleHelper>(List.of(new PuzzleHelper(start[0], start[1], start[0], start[1])));

        for(long i = 0; i< stopStep; i++){
            if(i%exp==0 && i !=0){
                System.out.println("step "+i+" size "+steps.size()+" mod "+(steps.size()/i));
                exp = exp *2;
            }
            steps = TravelPath(steps);
            //steps.stream().map(ph -> ph.getPos()).collect(Collectors.toSet())
            
        }

        return steps.size();
    }

    public int solution2(List<String> input){
        return 0;
    }

    public int[] findS(List<String> input){
        for(int i = 0; i < input.size(); i++){
            if(input.get(i).indexOf("S")> -1)
                return new int[]{i, input.get(i).indexOf("S")};
        }
        return null;
    }


    public List<PuzzleHelper> TravelPath(List<PuzzleHelper> path){
        currentSteps = new HashSet<String>();
        List<PuzzleHelper> nextPath = path.stream()
            .map(PuzzleHelper::progressRoute)
            .flatMap(phList->phList.stream())
            .collect(Collectors.toList());

        return nextPath;
    }

    
    public class PuzzleHelper{

        int lineNumber;
        int linePos;
        long imaginaryLine;
        long imaginaryPos;
        int step;

        public PuzzleHelper(int lineNumber, int linePos, long imaginaryLine, long imaginaryPos){
            this.lineNumber =lineNumber;
            this.linePos = linePos;
            this.imaginaryLine = imaginaryLine;
            this.imaginaryPos = imaginaryPos;
        }

        public PuzzleHelper(int lineNumber, int linePos){
            this.lineNumber =lineNumber;
            this.linePos = linePos;
            this.imaginaryLine = 0;
            this.imaginaryPos = 0;
        }

        public List<PuzzleHelper> progressRoute(){
            List<Optional<PuzzleHelper>> nextMoves = new ArrayList<Optional<PuzzleHelper>>();

                nextMoves.add(getNextDirection('L'));
                nextMoves.add(getNextDirection('U'));
                nextMoves.add(getNextDirection('D'));
                nextMoves.add(getNextDirection('R'));
        
            return nextMoves.stream().filter(ph-> !ph.isEmpty())
                .map(ph-> ph.get())
                .collect(Collectors.toList());

        }


        private Optional<PuzzleHelper> getNextDirection(char dir){
            int nextLine = this.lineNumber;
            int nextPos = this.linePos;
            //long nextImgLine = this.imaginaryLine;
            //long nextImgPos = this.imaginaryPos;
            switch(dir){
                case 'R':
                    if(linePos < linePosMax-1)
                        nextPos++;
                    else
                        Optional.empty();
                    //    nextPos = 0;
                    //nextImgPos++;
                    break;
                case 'L':
                if(linePos > 0)
                        nextPos--;
                    else
                        Optional.empty();
                        //nextPos = linePosMax-1;
                    //nextImgPos--;
                    break;
                case 'U':
                if(lineNumber > 0)
                        nextLine--;
                    else
                        Optional.empty();
                        //nextLine = lineNumberMax-1;
                    //nextImgLine--;
                    break;
                case 'D':
                if(lineNumber < lineNumberMax-1)
                        nextLine++;
                    else
                        Optional.empty();
                        //nextLine = 0;
                    //nextImgLine++;
                    break;
            }
            if(gardenMap.get(nextLine).charAt(nextPos)=='#')
                return Optional.empty();

            // if(currentSteps.contains(nextImgLine+","+nextImgPos))
            //     return Optional.empty();
            // else    
            //     currentSteps.add(nextImgLine+","+nextImgPos);

            return Optional.of(new PuzzleHelper(nextLine, nextPos));

        }

    }



}
