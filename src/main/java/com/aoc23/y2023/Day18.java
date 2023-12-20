package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aoc23.util.IDay;

public class Day18 extends IDay{
    
    public Map<Integer, ArrayList<Integer>> digMap;

    public Day18() {
        super("18");
        digMap = new HashMap<Integer, ArrayList<Integer>>();
    }

    public int solution1(List<String> input){
        digMap.put(0,  new ArrayList<Integer>());
        configureMap(input);
        int countHoles = countHoles();
        return countHoles;
    }
//43544 low
//44642
//45159
    public int solution2(List<String> input){
        return 0;
    }

    public void configureMap(List<String> input){
        int xPos = 60;
        int yPos = 188;
        int maxX = 0;
        int maxY = 0;
        int minX = 0;
        int minY = 0;

        
        for(String line : input){
            maxX = Math.max(maxX, xPos);
            maxY = Math.max(maxY, yPos);
            minX = Math.min(minX, xPos);
            minY = Math.min(minY, yPos);
            // if(yPos == 186)
            //     System.out.println("here");
            String[] splitString = line.split(" ");
            int move = Integer.parseInt(splitString[1]);
            switch(splitString[0]){
                case"U":
                    yPos-= move;
                    addYLineMap(yPos, xPos, move-1);
                    break;
                case"D":
                    addXLineMap(yPos, xPos, 0);
                    addXLineMap(yPos, xPos, 0);

                    addYLineMap(yPos, xPos, move);
                    yPos += move;
                    addXLineMap(yPos, xPos, 0);
                    addXLineMap(yPos, xPos, 0);
                    break;
                case"R":
                    addXLineMap(yPos, xPos, move);
                    xPos+= move;
                    break;
                case"L":
                    xPos-= move;
                    addXLineMap(yPos, xPos, move);
                    break;
            }
        }
        System.out.println("line:"+maxY+" "+maxX);
        System.out.println("line:"+minY+" "+minX);
        //y188 x60 - y233 x240

    }

    private void addYLineMap(int startY, int xPos, int move) {
        for(int i = 1; i <= move; i++){
            addXLineMap((startY+i), xPos, 0);
        }
    }

    private void addXLineMap(int yPos, int xPos, int move) {
        ArrayList<Integer> line = digMap.getOrDefault(yPos, new ArrayList<Integer>());
        for(int i = 0; i <= move; i++){
            line.add(xPos+i);
        }
        digMap.put(yPos, line);
    }
    
    private int countHoles() {
        int count = 0;
        digMap.keySet().stream().sorted();
        for(List<Integer> line: digMap.values()){
           count+= processLine(line);
        }
        return count;
    }

    public int processLine(List<Integer> line){
        if(line.size() == 0)
            return 0;
        line.sort(Integer::compare);
        int lineCount = 1;
        int startX = line.getFirst();
        boolean inside = true;
        int repeats = 0;
        String out = "";
        if(startX > 0)
            out += ".".repeat(startX);
        
        for(int i =1; i <line.size(); i++){
            int nextPos = line.get(i);
            if(startX == nextPos){
                repeats++;
                if(repeats ==2){
                    inside = !inside;
                    out = out.substring(0,out.length())+"D";
                    repeats = 0;
                }
                continue;
            }
            repeats = 0;

            if((nextPos - startX)== 1){
                lineCount++;
                out+="#";
            }
            else{
                String nextStr = ".";
                if(inside){
                    lineCount += Math.abs(nextPos - startX);
                    nextStr = "#";
                    
                }
                else{
                    lineCount++;
                }
                out+= "E"+nextStr.repeat(Math.abs(nextPos - startX));
                inside = !inside;
            }  
            startX = nextPos;                
        }

        // System.out.println("line:"+out);
        // System.out.println("line^"+lineCount+".."+line);
        return lineCount;
    }


}
