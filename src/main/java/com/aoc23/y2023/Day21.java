package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aoc23.util.IDay;

public class Day21 extends IDay{
    
    public List<String> gardenMap;
    public int lineNumberMax;
    public int linePosMax;
    public List<PuzzleHelper> steps;

    public Day21() {
        super("21");
    }

    public int solution1(List<String> input){
        lineNumberMax = input.size();
        linePosMax = input.get(0).length();
        int stopStep = 16;

        gardenMap = input;
        int[] start = findS(input);

        steps = new ArrayList<PuzzleHelper>(List.of(new PuzzleHelper(start[0], start[1], 0)));

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


    public List<PuzzleHelper> TravelPath(List<PuzzleHelper> path, int[][] puzzle){
        //new ArrayList<PuzzleHelper>(;
        path.stream().flatMap(PuzzleHelper::progressRoute)
        while(!path.isEmpty()){           
            PuzzleHelper current = path.poll();
            int currentSpace = puzzle[current.lineNumber][current.linePos];
            List<Optional<PuzzleHelper>> nextMoves = current.progressRoute(currentSpace);

            nextMoves.stream().filter(ph-> !ph.isEmpty()).forEach(ph -> path.add(ph.get()));

        }
        return null;
    }

    
    public class PuzzleHelper{

        int lineNumber;
        int linePos;
        int step;

        public PuzzleHelper(int lineNumber, int linePos, int step){
            this.lineNumber =lineNumber;
            this.linePos = linePos;
            this.step = step;
        }

        public List<PuzzleHelper> progressRoute(){
            List<PuzzleHelper> nextMoves = new ArrayList<Optional<PuzzleHelper>>();

            if(!(repeats >=9 && direction == 'L'))
                nextMoves.add(getNextDirection('L').or);
            if(!(repeats >=9 && direction == 'U'))
                nextMoves.add(getNextDirection('U'));
            if(!(repeats >=9 && direction == 'D'))
                nextMoves.add(getNextDirection('D'));
            if(!(repeats >=9 && direction == 'R'))
                nextMoves.add(getNextDirection('R'));


            return nextMoves;
        }

        private Optional<PuzzleHelper> getNextDirection(char dir){
            int nextLine = this.lineNumber;
            int nextPos = this.linePos;
            switch(dir){
                case 'R':
                    if(linePos < linePosMax-1)
                        nextPos++;
                    else
                        return Optional.empty();
                    break;
                case 'L':
                if(linePos > 0)
                        nextPos--;
                    else
                        return Optional.empty();
                    break;
                case 'U':
                if(lineNumber > 0)
                        nextLine--;
                    else
                        return Optional.empty();
                    break;
                case 'D':
                if(lineNumber < lineNumberMax-1)
                        nextLine++;
                    else
                        return Optional.empty();
                    break;
            }
            return Optional.of(new PuzzleHelper(nextLine, nextPos, step +1));

        }

    }



}
