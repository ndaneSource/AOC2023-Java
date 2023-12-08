package com.aoc23.y2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

public class Day8 extends IDay{
    
    public Map<String, Pair<String,String>> map;

    public Day8() {
        super("8");
        map = new HashMap<String, Pair<String,String>>();
    }

    public int solution1(List<String> input){
        buildMap(input);
        
        String position = "AAA";
        int steps = 0;
        while(!position.equals("ZZZ")){
            for(int i =0; i<input.get(0).length(); i++){
                String direction = input.get(0).substring(i,i+1);
                System.out.print(position+" "+direction+" -> ");
                if(position.equals("ZZZ"))
                    break;
                if(direction.equals("L"))
                    position = map.get(position).getValue0();
                else
                    position = map.get(position).getValue1();
                
                System.out.println(position);
                steps++;
            }
        }

        return steps;
    }

    private void buildMap(List<String> input) {
        input.stream().filter(line -> line.indexOf("=")>=0)
            .forEach(line-> {
                Pair<String,String> p = new Pair<String,String>(line.substring(7,10), line.substring(12, 15)); 
                map.put(line.substring(0,3), p);
            });
    }

    public int solution2(List<String> input){
        stepSolution2();
        buildMap(input);
        String[] positions = getAStart();
        int steps = 0;
        boolean searching = false;
        while(searching){
            for(int i =0; i<input.get(0).length(); i++){
                String direction = input.get(0).substring(i,i+1);
                //System.out.println("step "+steps);

                for(int j = 0; j< positions.length;j++){
                    
                    //System.out.print(positions[j]+" "+direction+" -> ");

                    if(direction.equals("L"))
                        positions[j] =map.get(positions[j]).getValue0();
                    else
                        positions[j] = map.get(positions[j]).getValue1();
                    
                    //System.out.println(positions[j]);
                }
                //System.out.println("\n\n");

                steps++;
                searching = keepSearching(positions, steps);
                if(!searching)
                    break;
            }
            //System.out.println("step "+steps+" positions "+positions.length);
            //if(steps> 100)
            //    break;
        }

        return steps;

    }

    public long stepSolution2(){
        List<Long> steps = List.of(14429L,20569L, 22411L, 13201L, 18113L, 18727L);
        long common = 20569L;
        boolean searching = true;
        while(searching){
            searching = false;
            for(Long step: steps){
                if(common%step != 0){
                    searching =true;
                    common +=20569L;
                }
            }
        }
        System.out.println("final step "+common);
        return common;
    }

    private String[] getAStart() {
        List<String> listStart= map.keySet().stream().filter(key-> key.charAt(2)=='A').collect(Collectors.toList());
        return listStart.toArray(new String[0]);
    }

    private boolean keepSearching(String[] positions, int steps){
        boolean keepGoing = false;
        for(int i=0; i<positions.length;i++){
            if(positions[i].charAt(2)!='Z')
                keepGoing= true;
            else
                System.out.println(positions[i]+" one found "+i +" steps "+steps);
        }
        return keepGoing;
    }
}
