package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.List;

import com.aoc23.util.IDay;

public class Day10 extends IDay{
    
    public Day10() {
        super("10");
    }

    public int solution1(List<String> input){
        int[] start = findS(input);

        ArrayList<String> loop = traverse(input, start, new ArrayList<String>(), "");
        //System.out.println(loop.toString());
        return loop.size()/2;
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
    public ArrayList<String> traverse(List<String> input, int[]position, ArrayList<String> path, String direction){
        int lineNumber = position[0];
        int posNumber = position[1];
        while(true){
        
        if(input.get(lineNumber).substring(posNumber, posNumber+1).equals("S") && path.size()>0)
                return path;

        path.add(lineNumber+","+posNumber);
        switch(input.get(lineNumber).substring(posNumber, posNumber+1))
        {
            case "-":
                if(direction.equals("L")){
                        posNumber++;
                        direction = "L";
                    }
                else{
                    int[] nextPos = new int[]{lineNumber, posNumber-1};
                    ArrayList<String> p = traverse(input, nextPos, path, "R");
                    
                    if(p!= null)
                        return p;
                }
                break;
            case "|":
                if(direction.equals("U")){
                    int[] nextPos = new int[]{lineNumber+1, posNumber+0};
                    ArrayList<String> p = traverse(input, nextPos, path, "U");
                    if(p!= null)
                        return p;
                }
                else{
                    int[] nextPos = new int[]{lineNumber-1, posNumber-0};
                    ArrayList<String> p = traverse(input, nextPos, path, "D");
                    if(p!= null)
                        return p;
                }
                break;
            case "J":
                if(direction.equals("U")){
                        int[] nextPos = new int[]{lineNumber-0, posNumber-1};
                        ArrayList<String> p = traverse(input, nextPos, path, "R");
                        if(p!= null)
                            return p;
                    }
                else{
                        int[] nextPos = new int[]{lineNumber-1, posNumber-0};
                        ArrayList<String> p = traverse(input, nextPos, path, "D");
                        if(p!= null)
                            return p;
                    }
                break;
            case "L":
                if(direction.equals("U")){
                        int[] nextPos = new int[]{lineNumber+0, posNumber+1};
                        ArrayList<String> p = traverse(input, nextPos, path, "L");
                        if(p!= null)
                            return p;
                    }
                else {
                        int[] nextPos = new int[]{lineNumber-1, posNumber-0};
                        ArrayList<String> p = traverse(input, nextPos, path, "D");
                        if(p!= null)
                            return p;
                    }
                break;
            case "F":
                if(direction.equals("D")){
                        int[] nextPos = new int[]{lineNumber+0, posNumber+1};
                        ArrayList<String> p = traverse(input, nextPos, path, "L");  
                        if(p!= null)
                            return p;
                    }
                else {
                       int[] nextPos = new int[]{lineNumber+1, posNumber+0};
                        ArrayList<String> p = traverse(input, nextPos, path, "U");
                        if(p!= null)
                            return p;
                    }
                break;
            case "7":
                //left and down
                if(direction.equals("D")){
                        int[] nextPos = new int[]{lineNumber-0, posNumber-1};
                        ArrayList<String> p = traverse(input, nextPos, path, "R");
                        if(p!= null)
                            return p;
                    }
                else {
                        int[] nextPos = new int[]{lineNumber+1, posNumber+0};
                        path = traverse(input, nextPos, path, "U");
                        break;
                    }
                break;
            case "S":
                int[] nextPos = new int[]{lineNumber+0, posNumber-1};
                return traverse(input, nextPos, path,"R");
                
            default:
                return path;
        }
    }
        return path;
    }

    // private boolean canSearch(int pos, int lineNumber, List<String> input) {
    //     if(pos <0 || pos >= input.get(0).length())
    //         return false;

    //     if(lineNumber <0 || pos >= input.size())
    //         return false;
        
    //     // if(path.contains(lineNumber+","+pos))
    //     //     return false;

    //     return true;
    // }

}
