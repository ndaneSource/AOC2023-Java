package com.aoc23.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aoc23.util.IDay;

public class Day7 extends IDay{
    
    public List<Hand> handList;

    public Day7() {
        super("7");
    }

    public int solution1(List<String> input){
        handList = input.stream().map(line -> new Hand(line)).collect(Collectors.toList());
        List<Integer> bids = handList.stream().sorted((a,b)-> a.compareHand(b))
        .map(hand -> hand.bid).toList();

        int total=0;
        handList.stream().forEach(hand-> System.out.println(hand.geString()));
        System.out.println(bids);
        for(int i =0; i<bids.size();i++){
            total+= bids.get(i)*(i+1);
        }

        return total;
    }

    public int solution2(List<String> input){
        return 0;
    }

    

    public class Hand{
        int bid;
        int value;
        Map<String,Integer> cardMap;
        String cards;
        List<Integer> cardValues;
        
        public Hand(String input){
            String[] line = input.trim().split("\\s+");
            this.cards = line[0].trim();
            this.bid = Integer.parseInt(line[1].trim());
            createCardMap();
            
        }

        public void createCardMap()
        {
            this.cardMap = new HashMap<String, Integer>();
            this.cardValues = new ArrayList<Integer>();
            int jokers = 0;
            for(String eachChar: cards.split("")){
                if(eachChar.equals("J")){
                    jokers++;
                    cardValues.add(getCardValue(eachChar));
                    continue;
                }
                int count = cardMap.getOrDefault(eachChar, 0);
                cardMap.put(eachChar, count+1);
                cardValues.add(getCardValue(eachChar));
            }
            if(cardMap.size()==0){
                cardMap.put("J",0);
            }

            String highestCount = cardMap.keySet().stream()
            .max((String a, String b)-> 
                cardMap.get(a).compareTo(cardMap.get(b))
            ).orElse("0");


            cardMap.put(highestCount, cardMap.get(highestCount)+jokers);
            switch (cardMap.get(highestCount)){
                case 5:
                    this.value = 7;
                    break;
                case 4:
                    this.value =6;
                    break;
                case 3:
                    if(cardMap.size() == 2){
                        this.value = 5;
                        break;
                    }
                    else
                        this.value = 4;
                        break;
                case 2:
                    if(cardMap.size()== 3){
                        this.value = 3;
                        break;
                    }
                    else
                        this.value = 2;
                        break;
                default:
                    this.value = 1;
                break;
            }

        }
    
        public int compareHand(Hand other){
            if(other.value > this.value)
                return -1;
            else if(this.value > other.value)
                return 1;

            for(int i =0; i < this.cards.length(); i++){
                int compare = Integer.compare(this.cardValues.get(i), other.cardValues.get(i));
                if(compare == 0)
                    continue;
                else
                    return compare;
            }
            return 0;
        }
        
        public int getCardValue(String card){
            switch(card){
                case "A":
                    return 14;
                case "K":
                    return 13;
                case "Q":
                    return 12;
                case "J":
                    //return 11;
                    return 0;
                case "T":
                    return 10;
                default:
                    return Integer.parseInt(card);
            }
        }
    
        public String geString(){
            return value+" "+cardValues;
        }
    }
}
