package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day23 extends IDay{
    
    public Map<String, Integer> cubeMap;
    public int lineNumberMax;
    public int linePosMax;
    public List<String> puzzleMap;

    public Day23() {
        super("23");
    }

    public int solution1(List<String> input){
        return 0;
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
        
    public class PuzzleHelper{

        int lineNumber;
        int linePos;
        long imaginaryLine;
        long imaginaryPos;
        int step;

        public PuzzleHelper(int lineNumber, int linePos){
            this.lineNumber =lineNumber;
            this.linePos = linePos;

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
            if(puzzleInput.get(nextLine).charAt(nextPos)=='#')
                return Optional.empty();

            // if(currentSteps.contains(nextImgLine+","+nextImgPos))
            //     return Optional.empty();
            // else    
            //     currentSteps.add(nextImgLine+","+nextImgPos);

            return Optional.of(new PuzzleHelper(nextLine, nextPos));

        }

    }


}
