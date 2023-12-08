package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aoc23.y2023.Day4;

public class Day4Test {

    @Test
	public void shouldGetCardValue() {
		Day4 d = new Day4();
        String test = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        int output = d.getCardValue(test);
        System.out.println(test);
        assertEquals(8, output);
	}

    @Test
	public void shouldGetZero() {
		Day4 d = new Day4();
        String test = "Card 1: 41 48 83 86 17 | 22 22 22 22 22";
        int output = d.getCardValue(test);
        System.out.println(test);
        assertEquals(0, output);
	}

    @Test
	public void shouldGetOne() {
		Day4 d = new Day4();
        String test = "Card 1: 41 48 83 86 17 | 48 22 22 22 22 22";
        int output = d.getCardValue(test);
        System.out.println(test);
        assertEquals(1, output);
	}

}
