package com.aoc23.y2023;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day14 extends IDay{
    
    public List<Lane> lanes;
    public Map<String,Integer> memory;
    public int iteration;
    public int weight;
    public Day14() {
        super("14");
        memory = new HashMap<String,Integer>();
        iteration = 0;
        weight = 0;
    }

    //112433 low
    public int solution1(List<String> input){
        buildLanes(input.size(), input.get(0).length());
        placeBoulders(input);
        for(int i =0; i< 160; i++){
            for(int j = 0; j <4; j++){
                getWeight();
                spin();
                input = createNewInput();
                buildLanes(input.size(), input.get(0).length());
                placeBoulders(input);
                //input.stream().forEach(System.out::println);
                //System.out.println(i);     
            }
            getWeight();
            writeMemory(i);
        }
        

        return weight;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public void buildLanes(int laneLength, int laneCount){
        lanes = new ArrayList<Lane>();
        for(int i =0; i < laneCount; i++){
            lanes.add(new Lane(laneLength));
        }
    }

    public void placeBoulders(List<String>input){
        for(int i = 0; i< input.size(); i++){
            String line = input.get(i);
            for(int pos =0; pos< line.length(); pos++){
                lanes.get(pos).addChar(i, line.charAt(pos));
            }

        }
        return ;

    }

    public void getWeight(){
        this.weight = lanes.stream().map(lane-> lane.calculateLoad()).reduce(Integer::sum).orElse(0);
    }

    public void writeMemory(int reps){
        String field = lanes.stream().map(lane-> lane.getString()).collect(Collectors.joining());
        int repeats = Optional.ofNullable(memory.get(field)).orElse(0);
        //int weight = lanes.stream().map(lane-> lane.calculateLoad()).reduce(Integer::sum).orElse(0);

        if(repeats > this.iteration){
            System.out.println(repeats+" "+reps+" "+weight);
            this.iteration++;
        }

        memory.put(field, repeats+1);
    }



    public void spin(){
        lanes.stream().forEach(Lane::spinUp);
    }

    public List<String> createNewInput(){
        return lanes.stream().map(Lane::getString).collect(Collectors.toList());
    }

 
    public class Lane{

        public String[] positions;
        public Lane(int length){
            positions = new String[length];
        }

        public void addChar(int pos, char c){
            positions[pos] = String.valueOf(c);
        }

        public int calculateLoad(){
            int sum = 0;
            for(int i = 0; i < positions.length; i++){
                if(positions[i].equals("O")){
                    sum += positions.length - i;
                }
            }
            return sum;
        }

        public void spinUp(){
            int openSpace = 0;
            for(int i = 0; i< positions.length; i++){
                if(i == openSpace){
                    if(positions[i].equals("O"))
                        openSpace++;
                }

                if( positions[i].equals("O") && openSpace < i){
                    positions[i] = ".";
                    positions[openSpace] = "O";
                    openSpace++;
                }else if(positions[i].equals("#")){
                    openSpace = i+1;
                }
            }
        }
        public String getString(){
            return Arrays.asList(positions).reversed().stream().collect(Collectors.joining());
        }
    }

}
