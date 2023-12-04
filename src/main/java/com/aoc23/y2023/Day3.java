package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import com.aoc23.util.IDay;


public class Day3 extends IDay{
    
    String symbolRegex;
    String numberRegex;
    int maxLength;

    public Set<Pair<Integer, Integer>> searchParts;

    //486142 incorrect
    //508053
    //528819 correct
    public Day3() {
        super("3");
        symbolRegex = "[*#$+%&=/@^-]";
        numberRegex = "[0-9]";
        searchParts = new HashSet<Pair<Integer, Integer>>();
    }

    public int solution1(List<String> input){
        int sum = 0;
        final int max = input.get(0).length()-1;
        //get all symbols
        for(int i =0; i < input.size(); i++){
            List<Integer> lineSymbols= getSymbols(input.get(i));
            final int line = i;
            lineSymbols.stream().forEach(symb-> addSearches(line, symb, max, input.size()-1));
        }

        
        Set<Pair<Integer,Integer>> filtered = searchParts.stream()
        .filter(pair -> input.get(pair.getValue0()).substring(pair.getValue1(), pair.getValue1()+1).matches("[0-9]"))
        .map(pair->{
            String line = input.get(pair.getValue0());
            int position = pair.getValue1();
            while(true){
                if(position > 0 && line.substring(position-1, position).matches("[0-9]") )
                    position -= 1;
                else
                    return new Pair<Integer,Integer>(pair.getValue0(), position);
            }
            
        }).collect(Collectors.toSet());
        System.out.println(filtered);
        sum = filtered
        .stream().map(pair -> getPart(input.get(pair.getValue0()), pair.getValue1()))
        //.peek(System.out::println)
        .reduce(Integer::sum).orElse(0);
        

        return sum;
    }

    


    public int solution2(List<String> input){
        symbolRegex = "[*]";
        maxLength = input.get(0).length();
        int sum = 0;
        final int max = input.get(0).length()-1;
        //get all symbols
        for(int i =0; i < input.size(); i++){
            List<Integer> lineSymbols= getSymbols(input.get(i));
            final int startLine = i ==0? 0:i-1;
            final int endLine = i == input.size()-1? i+1: i+2;
            System.out.println(i);
            sum+= lineSymbols.stream()
                .map(gear-> getGearValue(gear, input.subList(startLine, endLine)))
                .reduce(Integer::sum).orElse(0);
        }

        

        return sum;

    }

    private int getGearValue(int gear, List<String> subList) {
 
        int part = 1;
        int partCount = 0;
        int start = gear ==0 ? 0:gear-1;
        int end = gear == maxLength-1? gear+1: gear+2;
        Pattern reg = Pattern.compile(numberRegex+"+");

  
        for(String line : subList){
            Matcher match = reg.matcher(line.substring(start, end));
            while(match.find()){
                int pos = match.start();
                part = part * getPart(line, getStart(line, gear+pos));
                partCount++;
            }
        }
       
        return partCount == 2? part:0;
    }

    private int getStart(String line, int pos) {
        // if(pos == 0)
        //     return 0;
        // return line.substring(0,pos).lastIndexOf(".")+1;

        while(pos > 0 && line.substring(pos-1, pos).matches("[0-9]")){
                pos -= 1;
        }
        return pos;
    }

    public List<Integer> getSymbols(String line) {
        List<Integer> symbols= new ArrayList<>();
        int i = 0;
        Pattern reg = Pattern.compile(symbolRegex);
        Matcher match = reg.matcher(line);
        while(match.find()){
            symbols.add(match.start());
        }
        return symbols;
    }

    public void addSearches(int line, int position, int max, int lastLine){
        
        List<Integer> lineRange = IntStream.range(
                position == 0? 0:position-1,
                position+1 == max? max+1: position+2
            ).boxed().collect(Collectors.toList());
        //Observable.fromArray(lineRange).forEach(i-> searchParts.add(new Pair<Integer,Integer>(line-1, i)));
        if(line > 0)
            lineRange.forEach(i-> searchParts.add(new Pair<Integer,Integer>(line-1, i)));
        
        lineRange.forEach(i-> searchParts.add(new Pair<Integer,Integer>(line, i)));
        
        if(line < lastLine)
            lineRange.forEach(i-> searchParts.add(new Pair<Integer,Integer>(line+1, i)));

    }


    public int getPart(String line, int pos){
        //String part = line.substring(pos, line.indexOf(".", pos));
        String part = line.substring(pos).split("[^0-9]")[0];
        return Integer.parseInt(part);
    }
    
    

}
