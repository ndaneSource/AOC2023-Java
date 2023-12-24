package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day23 extends IDay{
    
    public Map<String, Integer> distanceMap;
    public int lineNumberMax;
    public int linePosMax;
    public List<String> puzzleInput;

    public Day23() {
        super("23");
        distanceMap = new HashMap<String, Integer>();
    }

    public int solution1(List<String> input){
        lineNumberMax = input.size();
        linePosMax = input.get(0).length();
        int[] start = findS(input);

        
        return 0;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public List<PuzzleHelper> TravelPath(List<PuzzleHelper> path){
        List<PuzzleHelper> nextPath = path.stream()
            .map(PuzzleHelper::progressRoute)
            .flatMap(phList->phList.stream())
            .collect(Collectors.toList());

        return nextPath;
    }
    public int[] findS(List<String> input){
        return new int[]{lineNumberMax-1, input.get(lineNumberMax-1).indexOf(".")};
    }
        
    public class PuzzleHelper{

        int lineNumber;
        int linePos;
        long imaginaryLine;
        long imaginaryPos;
        int step;
        Set<String> myPath;

        public PuzzleHelper(int lineNumber, int linePos, int step, Set<String> myPath){
            this.lineNumber =lineNumber;
            this.linePos = linePos;
            this.myPath = myPath;
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
                    if(linePos < linePosMax-1){
                        nextPos++;
                        if(nextChar(nextLine, nextPos) == '>')
                            return Optional.empty();
                    }
                    else
                        return Optional.empty();
                    //    nextPos = 0;
                    //nextImgPos++;
                    break;
                case 'L':
                if(linePos > 0)
                        nextPos--;
                    else
                        return Optional.empty();
                        //nextPos = linePosMax-1;
                    //nextImgPos--;
                    break;
                case 'U':
                if(lineNumber > 0)
                        nextLine--;
                    else
                        return Optional.empty();
                        //nextLine = lineNumberMax-1;
                    //nextImgLine--;
                    break;
                case 'D':
                if(lineNumber < lineNumberMax-1){
                        nextLine++;
                        if(nextChar(nextLine, nextPos) == 'v')
                            return Optional.empty();
                    }
                    else
                        return Optional.empty();
                        //nextLine = 0;
                    //nextImgLine++;
                    break;
            }
            if(nextChar(nextLine, nextPos)=='#')
                return Optional.empty();

            if(myPath.contains(nextLine+","+nextPos)){
                return Optional.empty();
            }
            else    
                myPath.add(nextLine+","+nextPos);

            return Optional.of(new PuzzleHelper(nextLine, nextPos, step+1, myPath));

        }

        private char nextChar(int nextLine, int nextPos){
            return puzzleInput.get(nextLine).charAt(nextPos);
        }

    }


}
