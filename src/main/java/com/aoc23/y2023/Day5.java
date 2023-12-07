package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import com.aoc23.util.IDay;

public class Day5 extends IDay{
    
    public List<Pair<Long,Long>> seedMap;

    public Day5() {
        super("5");
        seedMap = new ArrayList<Pair<Long,Long>>();
    }


    public int solution1(List<String> input){
        // get seed numbers
        List<String> seedsString = Arrays.asList(input.get(0).split(":")[1].trim().split(" "));
        List<Seed> seeds = seedsString.stream().map(seed-> new Seed(seed)).collect(Collectors.toList());

        for(String line: input){
            if(line.contains("-to-")){
                seeds.stream().forEach(seed-> seed.nextSection());
                continue;
            }

            if(line.trim().length() == 0 || line.contains("seed")){
                continue;
            }

            String[] map = line.trim().split(" ");
            long start = Long.parseLong(map[1]);
            long target = Long.parseLong(map[0]);
            long range = Long.parseLong(map[2]);
            seeds.stream().forEach(seed-> seed.translate(start, target, range));
        }

        
        long result = seeds.stream().map(seed-> seed.getSeed()).reduce(Math::min).orElse(0L);
        System.out.println("result "+result);



        return 0;
    }

    public int solution2(List<String> input){
        this.createSeedMap(input.get(0));
        List<List<Range>> map = new ArrayList<List<Range>>();
        List<Range> ranges = new ArrayList<>();
        for(String line: input){
            if(line.contains("-to-") && ranges.size()> 0){
                ranges = ranges.stream().sorted((r1, r2)-> r1.getLow() < r2.getLow()? -1:1).collect(Collectors.toList());
                //.forEach(r-> r.getRange());
                if(ranges.get(0).target >0){
                    long low = ranges.get(0).target;
                    ranges.add(0, new Range("0 0 "+low));
                }

                long high = ranges.get(ranges.size()-1).targetEnd;
                ranges.add(new Range( high+" "+high+" "+(Long.MAX_VALUE - high)));
                
                map.add(ranges);
                ranges = new ArrayList<Range>();
                continue;
            }

            if(line.trim().length() == 0 || line.contains("seed")){
                continue;
            }

            ranges.add(new Range(line));

        }
        ranges = ranges.stream().sorted((r1, r2)-> r1.getLow() < r2.getLow()? -1:1).collect(Collectors.toList());
        if(ranges.get(0).start >0){
            long low = ranges.get(0).target;
            ranges.add(0, new Range("0 0 "+low));
        }
        map.add(ranges);

        long low = findLowest(map, map.size()-1, 0L, Long.MAX_VALUE, "stack:0-max");
        low = forwardConvert(map, low);
        System.out.println("low is "+low);
        //ranges.stream().sorted((r1, r2)-> r1.getLow() < r2.getLow()? -1:1).forEach(r-> r.getRange());

    

        return 0;
    }

    public void createSeedMap(String line) {
        String[] seeds = line.split(":")[1].trim().split(" ");
        for(int i =0; i< seeds.length; i= i+2){
            long start = Long.parseLong(seeds[i]);
            long end = start + Long.parseLong(seeds[i+1]);
            seedMap.add(new Pair<Long,Long>(start, end));
        }
    }


    public long findLowest(List<List<Range>> maps, int index, long start, long stop, String stack){
        long ret= -1;
        long count =0;
        for(Range r : maps.get(index)){
            List<Pair<Long,Long>> matches = getMatches(r, start, stop);
            for(Pair<Long,Long> match:matches)
                if(index == 0){
                    ret = findSeedMatch(match.getValue0(), match.getValue1());
                }else{
                    ret = findLowest(maps, index-1, match.getValue0(), match.getValue1(), stack+ ", "+r.toString()+match.getValue0()+"-"+match.getValue1());
                }
            System.out.println(ret+" "+index+" "+count+", "+start+" "+stop+", "+stack+r.toString());
            if(ret != -1)
                return ret;
            
            count ++;
        }
        return ret;

    }


    public List<Pair<Long,Long>> getMatches(Range range, long start, long end) {
        List<Pair<Long,Long>> matches = new ArrayList<>();
        
        if(start <= range.target && end >= range.target){
            if(end >= range.targetEnd){
                matches.add(new Pair<Long,Long>(range.start,range.end));
            }else {
                long diff = range.targetEnd - end;
                matches.add(new Pair<Long,Long>(range.start,range.end-diff));
            }
        }else if(start <= range.targetEnd && end >= range.targetEnd){
            if(start <= range.target)
                matches.add(new Pair<Long,Long>(range.start,range.end));
            else{
                long diff = start - range.target;
                matches.add(new Pair<Long,Long>(range.start+diff,range.end));
            }
        }else if(start >=range.target && end <= range.targetEnd){
            long diff = start - range.target;
            long diffEnd = range.targetEnd-end;

            matches.add(new Pair<Long,Long>(range.start +diff,range.end-diffEnd));
        }


        return matches;
    }


    public long findSeedMatch(Long start, Long end) {
        for(Pair<Long,Long> seed:seedMap){
            if(start <= seed.getValue0() && end >= seed.getValue0()){
                return seed.getValue0();
            }else if(start <= seed.getValue1() && end >= seed.getValue1())
                if(start > seed.getValue0())
                    return seed.getValue0();
                else
                    return start;
            else if(start >= seed.getValue0() && end <= seed.getValue1())
                return start;
        }
        return -1;
    }

    public long forwardConvert(List<List<Range>> maps, Long start) {
        for(List<Range> convert: maps){
            for(Range r: convert){
                if(start >= r.start && start <= r.end){
                    long start2 = r.convert(start);
                    System.out.println(r.toString()+" "+start +" to "+ start2);
                    start = start2;
                    break;
                }
            }
        }
        return start;
    }


    public class Seed{

        private long seed;
        private boolean ready;
        public Seed(String seed){
            ready = true;
            this.seed = Long.parseLong(seed);
        }

        public void translate(long start, long target, long range){
            if(!ready)
                return;

            long diff = seed - start;
            if(diff <0 || diff > range)
                return;
            else{
                seed = diff + target;
                ready = false;
                return;
            }

        }

        public void nextSection(){
            this.ready = true;
        }

        public long getSeed(){return seed;}
    }

    public Range createRange(String input){
        return new Range(input);
    }

    public class Range{
        long start;
        long target;
        long targetEnd;
        long range;
        long end;
        public Range(String line){
            String[] map = line.trim().split(" ");
            this.start = Long.parseLong(map[1]);
            this.target = Long.parseLong(map[0]);
            this.range = Long.parseLong(map[2]);
            this.end = start+range;
            this.targetEnd = target+range;
        }
        public void getRange(){
            String p = start+" - "+end+" to " +target+" - "+targetEnd;
            System.out.println(p);
        }
        public long getLow(){
            return target;
        }
        public long convert(long input){
            return target + (input - start);
        }
        public String toString(){
            return "["+target+","+start+","+range+"]";
        }
    }
}
