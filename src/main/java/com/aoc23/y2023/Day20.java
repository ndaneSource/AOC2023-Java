package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day20 extends IDay{
    
    public Map<String, Pulser> communicationMap;
    public int lowPulseCount;
    public int rxLowCount;
    public int highPulseCount;

    public Set<String> states;
    public Queue<Pulser> commandQueue;
    public int loops;

    public Day20() {
        super("20");
        communicationMap = new HashMap<String, Pulser>();

    }
    //wrong 887,810,606
    //886701120 1000 loops
    //886701120 answer
    //228134431501037
    public int solution1(List<String> input){
        setupPulsers(input);

        //states = new HashSet<String>();
        //states.add(getAllState());
        rxLowCount = 0;

        //System.out.println("init state zh "+communicationMap.get("zh").getState());

        int countPerLoop = findLoop();

        int intRatio = 1;//(1000/countPerLoop);
        long countPerThousand = intRatio * intRatio * (highPulseCount * lowPulseCount);
        System.out.println(countPerThousand);
        return 0;
    }

    public int solution2(List<String> input){
        return 0;
    }

    public void setupPulsers(List<String> input){

        for(String line: input){
            String[] inputSplit = line.split("->");
            String type = inputSplit[0].substring(0,1);
            String name = inputSplit[0].substring(1).trim();
            String sendsTo = inputSplit[1];

            switch(type){
                case "%":
                    communicationMap.put(name, new FlipFlop(name, "f", sendsTo));
                    break;
                case "&":
                    communicationMap.put(name, new Conjunction(name, "c", sendsTo));
                    break;
                default:
                    communicationMap.put(inputSplit[0].trim(), new Pulser(inputSplit[0].trim(), "p", sendsTo));
                    break;
            }
        }
        communicationMap.values().stream().forEach(Pulser::sendRegister);

    }

    public int findLoop() {
        boolean loopFound = false;
        loops = 0;
        rxLowCount =0;
        while(!loopFound){
            loops++;
            commandQueue = new LinkedList<Pulser>();
            commandQueue.add(communicationMap.get("broadcaster"));
            this.lowPulseCount++;
            while(!commandQueue.isEmpty()){
                List<Optional<Pulser>> nextSignals = commandQueue.poll().sendPulse();
                nextSignals.stream().filter(x->x.isPresent()).forEach(signal-> commandQueue.add(signal.get()));

            }
            if(rxLowCount ==4){
                System.out.println("one rx "+loops);
                break;
            }else if(rxLowCount <4 && rxLowCount > 0){
                 System.out.println("less rx loops "+loops+" rx "+rxLowCount);
            }
                rxLowCount =0;

            if(loops == 14707081)
                System.out.println("here");

            //String newState = getAllState();
            //loopFound = this.states.contains(newState);
            //System.out.println(this.initState);
            final int l = loops;
            //communicationMap.values().stream().forEach(p-> p.getLoop(l));

            if(loops%100000 == 0){
                System.out.println("loop - "+loops);
                //communicationMap.values().stream().forEach(Pulser::printReset);
            }
            // if(loops == 3072)
            //      System.out.println(communicationMap.get("ml").getState());
            //     System.out.println(communicationMap.get("bp").getState());
            // }

        }
        return loops;
    }

    public String getAllState(){
        return String.join(";", communicationMap.values().stream().map(Pulser::getState).collect(Collectors.toList()));
    }

    public class Pulser{
        List<String> sendTo;
        boolean isHighState;
        String myName;
        String type;
        List<String> resetLoops;
        String initStat="";
        int lowPulses;
        public Pulser(String name, String type, String senderCsv){
            this.isHighState = false;
            this.type = type;
            this.myName = name.trim();
            this.resetLoops = new ArrayList<String>();

            configSenders(senderCsv);
        }
        public Pulser(){};

        public void configSenders(String senderCsv){
            this.sendTo = List.of(senderCsv.split(", ")).stream().map(String::trim).collect(Collectors.toList());
        }

        public List<Optional<Pulser>> sendPulse(){
            
            if(isHighState)
                highPulseCount+= sendTo.size();
            else{
                lowPulseCount+= sendTo.size();
                if(myName.equals("zh"))
                    rxLowCount++;
            }

            if("xc,bp,pd,th".contains(myName)&& isHighState){
                //System.out.println(myName+" one toward zh");
                rxLowCount++;
            }
            
            
            // if("gd,rm,sl,cp,qv,kv,jn,ll".contains(myName))
            //System.out.println(type+" "+myName+" sending "+ isHighState+" to "+String.join(", ", sendTo));
            // if(myName.equals("bp")&& isHighState)
            //     System.out.println("bp sending "+isHighState);

            return sendTo.stream().map(receiver -> 
                communicationMap.getOrDefault(receiver, new Pulser())
                .receivePulse(myName, isHighState))
            .collect(Collectors.toList());

        }
        
        public Optional<Pulser> receivePulse(String pulser, boolean isHighPulse){
            return Optional.empty();
        }

        public void sendRegister(){
            sendTo.stream().forEach(receiver -> communicationMap.getOrDefault(receiver, new Pulser()).receiveRegister(myName, isHighState));
        }
        public void receiveRegister(String name, boolean isHighState){
            //System.out.println(myName+" reg "+name);
        }

        public void queueSignal(){
            commandQueue.add(this);
        }

        public String getState(){
            if(initStat.equals(""))
                initStat = myName+"1";
            return myName+"1";
        }
        public void printReset(){
        }
        public void getLoop(int loop){
        }
    }

    public class FlipFlop extends Pulser{

        boolean isOn;
        public FlipFlop(String name, String type, String senderCsv){
            super(name, type, senderCsv);
            this.isOn = false;
        }

        public Optional<Pulser> receivePulse(String pulser, boolean isHighPulse){
            // if(myName.equals("ll"))
            //     System.out.println("here");
            if(isHighPulse)
                return Optional.empty();

            this.lowPulses++;
            this.isOn = !this.isOn;
            this.isHighState = !this.isHighState;
            return Optional.of(this);
            //queueSignal();
        }

        public String getState(){
            if(initStat.equals(""))
                initStat = myName+"2:"+isOn+":"+isHighState;

            return myName+"2:"+isOn+":"+isHighState;
            
        }

        public void printReset(){
            // if(!isOn && !isHighState)
            //     System.out.println(myName+" f at init state "+resetLoops.size()+" on loops "+String.join(", ", resetLoops));            
        }
        public void getLoop(int loop){
            // if(!isOn && !isHighState)
            //     this.resetLoops.add(loop+"");
        }
    }

    public class Conjunction extends Pulser{
        Map<String, Boolean> receiveFrom;
        public Conjunction(String name, String type, String senderCsv){
            super(name, type, senderCsv);
            receiveFrom = new HashMap<String, Boolean>();
            isHighState = false;
        }
        
        public Optional<Pulser> receivePulse(String pulser, boolean isHighPulse){
            
            updateReciever(pulser, isHighPulse);
            checkReceivers();

            // if(myName.equals("ml")&& !isHighState){
            //     System.out.print(myName+" received "+isHighPulse);
            //     System.out.println(" and state "+isHighState);
            // }

            return Optional.of(this);
        }
        public void configReceiveMap(List<String>recs){

        }
        public void updateReciever(String listenTo, boolean isHighPulse){
            this.receiveFrom.put(listenTo, isHighPulse);
        }
        public void checkReceivers(){
            // all high, send low
            //any low send high
            //for all false, count > 0
            this.isHighState = receiveFrom.values().stream().filter(v-> !v).count() > 0;
        }
        public void receiveRegister(String name, boolean isHighState){
            this.receiveFrom.put(name, false);
        }

        public String getState(){
            String receiveFromStr = String.join(":", receiveFrom.entrySet().stream().map(v-> v.getKey()+":"+v.getValue().toString()).collect(Collectors.toList()));
            
            String state= myName+"3:"+receiveFromStr;

            if(initStat.equals(""))
                initStat = state;

            return state;
        }


        public void printReset(){
            // if(resetLoops.size() > 0) 
            //     System.out.println(myName+" c at init state "+resetLoops.size()+" on loops "+String.join(", ", resetLoops));            
            // else
            //     System.out.println(myName+" c at init state, no loops");            
        }

        public void getLoop(int loop){
            if(getState().equals(initStat))
                this.resetLoops.add(loop+"");
        }

    }
}
