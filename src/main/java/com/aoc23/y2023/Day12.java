package com.aoc23.y2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aoc23.util.IDay;

public class Day12 extends IDay{
    
    Map<String,Long> memory;
    public Day12() {
        super("12");
        memory = new HashMap<String,Long>();
    }

    //253748996 too low
    //253748996
    public int solution1(List<String> input){
        long result = input.stream().map(line-> this.solveLine(line)).reduce(Long::sum).orElse(-1L);
        System.out.println("output: "+result);
        return 0;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public long solveLine(String line){
        String[] splitLine = line.split(" ");

        String puzzle = splitLine[0]+"?";
        puzzle = puzzle.repeat(5);
        puzzle = puzzle.substring(0, puzzle.length()-1);

        String keyStr = (splitLine[1]+",").repeat(5);
        List<Integer> keyList = this.splitToIntegers(keyStr, ",");
 
    

        long results = simplify(puzzle, keyList);
        System.out.println("line "+puzzle+ " "+keyList.toString()+ " --"+ results);
        if(results < 1)
            System.out.println("how?");
        return results;

    }

    public long simplify(String puzzleStr, List<Integer> keyList) {
        long count = 0;
        puzzleStr = trimDots(puzzleStr);

        long mem = remember(puzzleStr, keyList);
        if(mem >= -1)
            return mem;
        int[] metrics = getPuzzleMetrics(puzzleStr, keyList);
        if(metrics[4]==-1)
            return 0;

        if(keyList.size() ==1){
            if(metrics[1] == 0 && metrics[3] == 0)
                return metrics[2] - (metrics[0]-1);

            if(metrics[3] == 0 && puzzleStr.length() == keyList.get(0) )
                return 1;
        }

        if(keyList.size() == 0){
            if(metrics[1]> 0)
                return 0;
            else
                return 1;
        }

        int minRequired = 0;
        int keyAmount = keyList.get(0);
        int hashFound = 0;
        boolean last = false;
        int hashBreak = 10000;
        boolean end =false;
        String progress = "";
        for(int i =0; i< puzzleStr.length();i++){
            progress+= puzzleStr.charAt(i);
            if(keyList.size()==1 && i == hashBreak)
                break;
            end = i == puzzleStr.length()-1;
            last = end || i == hashBreak? true: false;
            switch(puzzleStr.charAt(i)){
            case '.':
                if(hashFound >0){                    
                    last = true;
                }
                minRequired++;
                break;
            case '#':
                if(hashFound==0){
                    hashBreak = i+keyAmount;
                }
                minRequired++;
                hashFound++;
                break;
            case '?':
                minRequired++;
            default:
                break;
            }

            boolean currentBufferPoint = end|| (!end && puzzleStr.charAt(i) != '#');

            if(keyAmount < minRequired && currentBufferPoint){
                long rightCount;
                if(keyList.size() ==2)
                    rightCount= simplifySingle(puzzleStr.substring(i+1), keyList.get(1));
                else
                    rightCount= simplify(puzzleStr.substring(i+1), keyList.subList(1, keyList.size()));
                if(rightCount >0)
                    count+=rightCount;
               
            }
            if(last)
                break;
            if(puzzleStr.charAt(i)=='.'){
                minRequired=0;
            }
        }
           
        commitMemory(puzzleStr, keyList, count);
        return count;
    }


     public long simplifySingle(String puzzleStr, Integer keyList) {
        long count = 0;
        puzzleStr = trimDots(puzzleStr);

        long mem = remember(puzzleStr, List.of(keyList));
        if(mem >= -1)
            return mem;
        int[] metrics = getPuzzleMetrics(puzzleStr, List.of(keyList));
        if(metrics[4]==-1)
            return 0;


        if(metrics[1] == 0 && metrics[3] == 0)
            return metrics[2] - (metrics[0]-1);

        if(metrics[3] == 0 && puzzleStr.length() == keyList)
            return 1;



        int minRequired = 0;
        int keyAmount = keyList;
        int hashFound = 0;
        int qs = 0;
        boolean bufferPoint = false;
        boolean last = false;
        int hashBreak = 10000;
        boolean end =false;
        String progress = "";
        for(int i =0; i< puzzleStr.length();i++){
            progress+= puzzleStr.charAt(i);
            if(i == hashBreak)
                break;
            end = i == puzzleStr.length()-1;
            last = end || i == hashBreak? true: false;
            switch(puzzleStr.charAt(i)){
            case '.':
                if(hashFound >0){                    
                    last = true;
                }
                minRequired =0;
                break;
            case '#':
                if(hashFound >0 && minRequired > keyAmount){
                    qs--;
                }else if(hashFound==0){
                    hashBreak = i+keyAmount;
                }
                minRequired++;
                hashFound++;
                break;
            case '?':
                if(hashFound ==0){
                    qs++;
                }
                minRequired++;
            default:
                break;
            }
            if(qs < 0)
                break;
            
            bufferPoint = end|| (!end && puzzleStr.charAt(i+1) != '#');

            if(keyAmount <= minRequired && bufferPoint){
                if(i == puzzleStr.length()-1){
                    count++;
                    break;
                }
               
                if(clearEnd(puzzleStr.substring(i+1)))
                    count++;
            }

            if(last)
                break;
            if(puzzleStr.charAt(i)=='.'){
                qs=0;
                minRequired=0;
            }
        }
           
        commitMemory(puzzleStr, List.of(keyList), count);
        return count;
    }

    public boolean clearEnd(String puzzle){
        if(puzzle.contains("#"))
            return false;
        
        return true;
    }


    public int[] getPuzzleMetrics(String puzzleStr, List<Integer> keyList){

        int hashNeeded = keyList.stream().reduce(Integer::sum).orElse(0);
        int hashAvailable = puzzleStr.replaceAll("[^#]", "").length();
        int qsAvailable = puzzleStr.replaceAll("[^?]", "").length();
        int dotsAvailable = puzzleStr.replaceAll("[^.]", "").length();

        int hashRatio = (hashNeeded < hashAvailable)|| (hashNeeded> hashAvailable+qsAvailable)? -1:0;

        return new int[]{hashNeeded, hashAvailable, qsAvailable, dotsAvailable, hashRatio};

    }

    public void commitMemory(String puzzle, List<Integer> keyList, Long count){
        String keyString = keyList.size()>0? keyList.toString(): "[]";
        memory.put(puzzle+"-"+keyString, count);
    }

    public long remember(String puzzle, List<Integer> keyList){
        String keyString = keyList.size()>0? keyList.toString(): "[]";
        return memory.getOrDefault(puzzle+"-"+keyString,-2L); 
    }

    public String trimDots(String puzzle){

        int start=-1, end = -1;
        int hasQ = puzzle.indexOf("?");
        int hasH = puzzle.indexOf("#");
        
        if(hasH >=0 && hasQ>=0){
            start = Math.min(puzzle.indexOf("?"), puzzle.indexOf("#"));
            end = Math.max(puzzle.lastIndexOf("?"), puzzle.lastIndexOf("#"))+1;
        }
        else if(hasQ >=0){
            start = puzzle.indexOf("?");
            end = puzzle.lastIndexOf("?")+1;
        }
        else if(hasH >=0){
            start = hasH;
            end = puzzle.lastIndexOf("#")+1;
        }
        else
            return "";

        return puzzle.substring(start, end);
        
    }



}
