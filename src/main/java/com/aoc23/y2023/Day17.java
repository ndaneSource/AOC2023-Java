package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import com.aoc23.util.IDay;

public class Day17 extends IDay{
    
    public Map<String, Integer> heatMap;
    int lineNumberMax;
    int linePosMax;
    int minFound;

    public Day17() {
        super("17");

    }

    public int solution1(List<String> input){
        lineNumberMax = input.size();
        linePosMax = input.get(0).length();
        minFound = Integer.MAX_VALUE;
        return setupRoute(input, 0, 1, 'R');
    }
    //1113 too high
    //1109 no
    //1098 no

    public int solution2(List<String> input){
        return 0;
    }

    public int setupRoute(List<String> input, int line, int pos, char direction){
        heatMap = new HashMap<String, Integer>();

        LinkedList<PuzzleHelper> path = new LinkedList<PuzzleHelper>();
        path.add(new PuzzleHelper(direction, line,pos, 0, "", 0,"", ""));

        int[][] puzzleInts = new int[input.size()][input.get(0).length()];
        for(int i = 0; i< lineNumberMax;i++){
            for(int j = 0; j < linePosMax; j++){
                puzzleInts[i][j] = Integer.parseInt(""+input.get(i).charAt(j));                
            }
        }

        TravelPath(path, puzzleInts);
        return minFound;
    }

    public void TravelPath(LinkedList<PuzzleHelper> path, int[][] puzzle){
        while(!path.isEmpty()){           
            PuzzleHelper current = path.poll();
            int currentSpace = puzzle[current.lineNumber][current.linePos];
            List<Optional<PuzzleHelper>> nextMoves = current.progressRoute(currentSpace);


            // if(current.tracking.length() > 20 && current.lineNumber+current.linePos < current.tracking.length()/10)
            //     continue;
            // if(current.tracking.equals("RRRRRRRRDDDDRRRRDDDDD"))
            //     System.out.println("here");
            if(endPath(current.getSpaceString(), current.heat))
                continue;
            if(current.lineNumber == lineNumberMax-1 && current.linePos == linePosMax-1){
                if(current.repeats < 3 || minFound < current.heat){
                    System.out.println(", nope "+current.heat);
                    continue;
                }
                System.out.println("\nbetter end found at heat "+(current.heat));
                System.out.println("path "+current.tracking+current.direction);
                //System.out.println("heat "+current.trackingHeat+currentSpace);
                minFound = Math.min(minFound, current.heat);
                continue;
            }
            
            //this.liteSpace.add(current.getSpaceString());
            nextMoves.stream().filter(ph-> !ph.isEmpty()).forEach(ph -> path.addFirst(ph.get()));

        }
    }

    public boolean endPath(String path, int heat){
        boolean end = true;

        if(this.heatMap.containsKey(path)){
            if(this.heatMap.get(path) > heat){
                this.heatMap.put(path, heat);
                end = false;
            }
        }
        else{
            end = false;
            this.heatMap.put(path, heat);
        }

        return end;
    
    }

    public class PuzzleHelper{

        char direction;
        int lineNumber;
        int linePos;
        int repeats;
        int heat;
        String path;
        String tracking;
        String trackingHeat;

        public PuzzleHelper(char direction, int lineNumber, int linePos, int repeats, String path, int heat, String tracking, String trackingHeat){
            this.direction = direction;
            this.lineNumber =lineNumber;
            this.linePos = linePos;
            this.repeats = repeats;
            this.path = path;
            this.heat = heat;
            this.tracking = tracking;
            this.trackingHeat = trackingHeat;
        }


        public String getSpaceString(){
            return String.format("%d%s,%d,%d", repeats,direction, lineNumber, linePos);
        }

        public boolean repeatPath(){
            String space = "("+lineNumber+","+linePos+")";
            if(path.contains(space))
                return true;
            
            path+=space;
            return false;
        }
        public List<Optional<PuzzleHelper>> progressRoute(int heat){
            this.heat += heat;
            this.trackingHeat+=heat;
            List<Optional<PuzzleHelper>> nextMoves = new ArrayList<Optional<PuzzleHelper>>();

            if(this.heat > 1200){
                System.out.println("too hot "+this.heat+" "+getSpaceString());
                return nextMoves;
            }
            if(this.heat > 900 && this.linePos+this.lineNumber < 200){
                System.out.println("too dumb "+this.heat+" "+getSpaceString());
                return nextMoves;
            }

            if(repeatPath())
                return nextMoves;

            if(repeats < 3)
                nextMoves.add(getNextDirection(direction));
            else{
                if(!(repeats >=9 && direction == 'L'))
                    nextMoves.add(getNextDirection('L'));
                if(!(repeats >=9 && direction == 'U'))
                    nextMoves.add(getNextDirection('U'));
                if(!(repeats >=9 && direction == 'D'))
                    nextMoves.add(getNextDirection('D'));
                if(!(repeats >=9 && direction == 'R'))
                    nextMoves.add(getNextDirection('R'));
            }

            return nextMoves;
        }

        private Optional<PuzzleHelper> getNextDirection(char dir){
            int nextLine = this.lineNumber;
            int nextPos = this.linePos;
            int nextRepeats = dir == direction? repeats+1:0;
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
            return Optional.of(new PuzzleHelper(dir, nextLine, nextPos, nextRepeats, path, heat, tracking+dir, trackingHeat));

        }

    }


}
