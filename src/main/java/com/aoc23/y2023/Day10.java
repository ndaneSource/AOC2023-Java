package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;

public class Day10 extends IDay{
    
    HashSet<String> leftSide = new HashSet<String>();
    HashSet<String> rightSide = new HashSet<String>();
    public Day10() {
        super("10");
    }

    public int solution1(List<String> input){
        int[] start = findS(input);

        ArrayList<String> loop = traverse(input, start, new ArrayList<String>(), "");
        //System.out.println(loop.toString());
        return Math.floorDiv(loop.size(), 2);
    }

    public int solution2(List<String> input){
        int[] start = findS(input);
        ArrayList<String> loop = traverse(input, start, new ArrayList<String>(), "");

        System.out.println((1)+","+58+"contained? "+leftSide.contains((1)+","+58));

        rightSide.removeAll(loop);
        leftSide.removeAll(loop);
        System.out.println((1)+","+58+"contained? "+leftSide.contains((1)+","+58));

        visualize(input, loop);
        //fillSpace(input);
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

        String pos = lineNumber+","+posNumber;
        path.add(pos);
        leftSide.remove(pos);
        rightSide.remove(pos);
        switch(input.get(lineNumber).substring(posNumber, posNumber+1))
        {
            case "-":
                if(direction.equals("L")){
                    leftSide.add((lineNumber-1)+","+posNumber);
                    rightSide.add((lineNumber+1)+","+posNumber);
                    posNumber++;
                    direction = "L";
                    
                }
                else{
                    leftSide.add((lineNumber+1)+","+posNumber);
                    rightSide.add((lineNumber-1)+","+posNumber);
                    posNumber--;
                    direction = "R";
                    
                }
                break;
            case "|":
                if(direction.equals("U")){
                    leftSide.add((lineNumber)+","+(posNumber+1));
                    rightSide.add((lineNumber)+","+(posNumber-1));
                    lineNumber++;
                    direction = "U";
                    
                }
                else{
                    leftSide.add((lineNumber)+","+(posNumber-1));
                    rightSide.add((lineNumber)+","+(posNumber+1));
                    direction = "D";
                    lineNumber--;
                    
                }
                break;
            case "J":
                if(direction.equals("U")){
                    leftSide.add((lineNumber+1)+","+(posNumber));
                    leftSide.add((lineNumber)+","+(posNumber+1));
                    posNumber--;
                    direction = "R";
                    
                }
                else{
                    direction = "D";
                    rightSide.add((lineNumber+1)+","+(posNumber));
                    rightSide.add((lineNumber)+","+(posNumber+1));
                    lineNumber--;
                    
                }
                break;
            case "L":
                if(direction.equals("U")){
                    rightSide.add((lineNumber+1)+","+(posNumber));
                    rightSide.add((lineNumber)+","+(posNumber-1));
                    posNumber++;
                    direction = "L";
                    
                }
                else {
                    direction = "D";
                    leftSide.add((lineNumber+1)+","+(posNumber));
                    leftSide.add((lineNumber)+","+(posNumber-1));
                    lineNumber--;
                    
                }
                break;
            case "F":
                if(direction.equals("D")){
                    leftSide.add((lineNumber-1)+","+(posNumber));
                    leftSide.add((lineNumber)+","+(posNumber-1));
                    posNumber++;
                    direction = "L";
                    if(lineNumber == 2)
                        System.out.println("here");
                    if(lineNumber == 2)
                        System.out.println((lineNumber-1)+","+posNumber+"contained? "+leftSide.contains((lineNumber-1)+","+posNumber));
                }
                else {
                    rightSide.add((lineNumber-1)+","+(posNumber));
                    rightSide.add((lineNumber)+","+(posNumber-1));
                    lineNumber++;
                    direction = "U";
                    
                }
                break;
            case "7":
                //left and down
                if(direction.equals("D")){
                    rightSide.add((lineNumber-1)+","+(posNumber));
                    rightSide.add((lineNumber)+","+(posNumber+1));
                    posNumber--;
                    direction = "R";
                    
                }
                else {
                    leftSide.add((lineNumber-1)+","+(posNumber));
                    leftSide.add((lineNumber)+","+(posNumber+1));
                    lineNumber++;
                    direction = "U";
                    
                }
                break;
            case "S":
                //test
                // rightSide.add((lineNumber-1)+","+(posNumber));
                // rightSide.add((lineNumber)+","+(posNumber-1)); 
                // posNumber++;
                // direction = "L";
                

                //input
                leftSide.add((lineNumber+1)+","+(posNumber));
                leftSide.add((lineNumber)+","+(posNumber+1));
                posNumber--;
                direction = "R";
                
                break;
            default:
                return path;
        }
    }
        //return path;
    }

    public void visualize(List<String> input, ArrayList<String>path){
        for(int i =0; i < input.size();i++){
            StringBuilder sb = new StringBuilder(input.get(i));

            boolean firstX = false;
            int lastX = -1;

            for(int j=0; j< input.get(i).length();j++){
                char test = sb.charAt(j);
                if(path.contains(i+","+j)){
                    //sb.setCharAt(j, 'X');
                    firstX = true;
                    lastX = j;
                }
                else if(leftSide.contains(i+","+j)){
                    sb.setCharAt(j, 'Z');
                }
                else if(rightSide.contains(i+","+j)){
                    sb.setCharAt(j, 'R');
                }
                else if(!firstX)
                    sb.setCharAt(j, 'O');
                else
                    sb.setCharAt(j, ' ');



            }
            if(lastX>0){
                int trailing = sb.length() - lastX-2;
                sb.replace(lastX+2, sb.length(), "O");
                sb.repeat("O", trailing);
            }
            input.set(i, sb.toString());
            if(i > 0)
              input = findOutside(input,i);
        }

        input.stream().forEach(System.out::println);
    }


    public List<String> findOutside(List<String> input, int line){
        Set<Pair<Integer,Integer>> searched = new HashSet<Pair<Integer,Integer>>();
        Queue<Pair<Integer,Integer>> searching= new PriorityQueue<Pair<Integer,Integer>>();

        StringBuilder sbLine = new StringBuilder(input.get(line));
        StringBuilder sbLinePrev = new StringBuilder(input.get(line-1));

        for(int i=0; i < sbLine.length(); i++){
            if(sbLine.charAt(i) == ' '){
                int maxLength = input.get(line-1).length()-1;

                for(int j =-1; j<2; j++)
                    if(i+j > 0 && i+j < maxLength){
                        if(sbLinePrev.charAt(i+j) == 'O'){
                            sbLine.setCharAt(i, 'O');
                            searching.add(new Pair<Integer,Integer>(line-1, i+j));
                            searching.add(new Pair<Integer,Integer>(line, i+j));  
                        }            
                    }
            }
            if(sbLine.charAt(i) == 'O'){
                int maxLength = input.get(line-1).length()-1;

                for(int j =-1; j<2; j++)
                    if(i+j > 0 && i+j < maxLength){
                            searching.add(new Pair<Integer,Integer>(line-1, i+j));
                            searching.add(new Pair<Integer,Integer>(line, i+j));  
                    }
            }
        }
        input.set(line, sbLine.toString());
        input.set(line-1, sbLinePrev.toString());

        while(searching.size()>0){
            Pair<Integer,Integer> pos = searching.poll();
            
            if(searched.contains(pos))
                continue;
            StringBuilder sb = new StringBuilder(input.get(pos.getValue0()));

            if(pos.getValue0() == 1 && pos.getValue1() == 58){
                System.out.println((1)+","+58+"contained??? "+leftSide.contains((1)+","+58) );
                System.out.println(sb.charAt(pos.getValue1()));
            }
            

            searched.add(pos);
            if(sb.charAt(pos.getValue1()) == ' '){
                sb.setCharAt(pos.getValue1(), 'O');
                input.set(pos.getValue0(), sb.toString());
                for(int x =-1; x<2; x++)
                    for(int y =-1; y<2; y++)
                        if(pos.getValue0()+x >0 && pos.getValue0()+x < input.size()-1 
                        && pos.getValue1()+y > 0 && pos.getValue1()+y <input.get(pos.getValue0()+x).length()-1)
                            searching.add(new Pair<Integer,Integer>(pos.getValue0()+x, pos.getValue1()+y));    
            }
        }
        //less 684, more 380

        //150R
        return input;
    }


    public void fillSpace(List<String> input){
        int count = 0;
        for(int i =0; i < input.size(); i++){
            StringBuilder sbLine = new StringBuilder(input.get(i));
            for(int j=0; j < sbLine.length(); j++){
                if(sbLine.charAt(j)=='R')
                    count++;

                    
            }

        }
    }
}
