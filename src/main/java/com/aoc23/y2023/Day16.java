package com.aoc23.y2023;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.aoc23.util.IDay;

public class Day16 extends IDay{
    
    int lineNumberMax;
    int linePosMax;
    Set<String> liteSpace;
    Set<String> loopDetector;

    public Day16() {
        super("16");
        
    }

    public int solution1(List<String> input){
        lineNumberMax = input.size();
        linePosMax = input.get(0).length();

        int maxLite = 0;

        for(int i=0; i < linePosMax; i++){
            maxLite = Math.max(maxLite, setupRoute(input, 0, i, 'D'));
            maxLite = Math.max(maxLite, setupRoute(input, lineNumberMax-1, i, 'U'));
        }

        for(int i=0; i < lineNumberMax; i++){
            maxLite = Math.max(maxLite, setupRoute(input, i, 0, 'R'));
            maxLite = Math.max(maxLite, setupRoute(input, i, linePosMax-1, 'L'));
        }
        
        return maxLite;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public int setupRoute(List<String> input, int line, int pos, char direction){
        liteSpace = new HashSet<String>();
        loopDetector = new HashSet<String>();
        Queue<LightHelper> light = new LinkedList<LightHelper>();
        light.add(new LightHelper(direction, line,pos));

        shineLight(light, input);
        return liteSpace.size();
    }

    public void shineLight(Queue<LightHelper> light, List<String> input){
        while(!light.isEmpty()){
            LightHelper current = light.poll();
            
            if(isLoop(current.getLoopString()))
                continue;
            
            this.liteSpace.add(current.getSpaceString());

            char currentSpace = input.get(current.lineNumber).charAt(current.linePos);
            List<Optional<LightHelper>> nextMoves = current.progressLight(currentSpace);
            nextMoves.stream().filter(lh-> !lh.isEmpty()).forEach(lh -> light.add(lh.get()));

        }
    }

    public boolean isLoop(String loopString){
        if(this.loopDetector.contains(loopString))
            return true;
        else{
            this.loopDetector.add(loopString);
            return false;
        }
    }


    public class LightHelper{

        char direction;
        int lineNumber;
        int linePos;

        public LightHelper(char direction, int lineNumber, int linePos){
            this.direction = direction;
            this.lineNumber =lineNumber;
            this.linePos = linePos;
        }

        public String getLoopString(){
            return String.format("%c,%d,%d",direction, lineNumber, linePos);
        }

        public String getSpaceString(){
            return String.format("%d,%d", lineNumber, linePos);
        }

        public List<Optional<LightHelper>> progressLight(char space){
            switch(space){
                case '.':
                    return List.of(directLight(direction));
                case '-':
                    return horizontalMirror();
                case '|':
                    return verticleMirror();
                case '/':
                    return leftMirror();
                case '\\':
                    return rightMirror();
                default:
                    return List.of();
            }

        }

        private Optional<LightHelper> directLight(char dir){
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
            return Optional.of(new LightHelper(dir, nextLine, nextPos));

        }

        private List<Optional<LightHelper>> horizontalMirror(){
            switch(direction){
                case 'R':
                case 'L':
                    return List.of(directLight(direction));
                case 'U':
                case 'D':
                    return List.of(directLight('R'), directLight('L'));
            }
            return null;
            
        }

        private List<Optional<LightHelper>> verticleMirror(){
            switch(direction){
                case 'U':
                case 'D':
                    return List.of(directLight(direction));
                case 'R':
                case 'L':
                    return List.of(directLight('D'), directLight('U'));
            }
            return null;
        }

        private List<Optional<LightHelper>> leftMirror(){
            switch(direction){
                case 'R':
                    return List.of(directLight('U'));
                case 'L':
                    return List.of(directLight('D'));
                case 'U':
                    return List.of(directLight('R'));
                case 'D':
                    return List.of(directLight('L'));
            }
            return null;
            
        }

        private List<Optional<LightHelper>> rightMirror(){
            switch(direction){
                case 'R':
                    return List.of(directLight('D'));
                case 'L':
                    return List.of(directLight('U'));
                case 'U':
                    return List.of(directLight('L'));
                case 'D':
                    return List.of(directLight('R'));
            }
            return null;
        }

        

    }
}
