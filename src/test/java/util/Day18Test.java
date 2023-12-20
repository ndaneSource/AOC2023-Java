package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import com.aoc23.y2023.Day18;

public class Day18Test {
    @Test
	public void shouldWorkWithTwoHumps() {
		Day18 d = new Day18();
        List<Integer> keys = new ArrayList<Integer>();
        keys.addAll(List.of(22, 31, 64, 155));
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        long output = d.processLine(keys);

        int expected = 102;
        assertEquals(expected, output);
	}

    @Test
	public void shouldWorkWithTwoHumps2() {
		Day18 d = new Day18();
        List<Integer> keys = new ArrayList<Integer>();
        keys.addAll(List.of(93, 103, 122, 125));
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        long output = d.processLine(keys);

        int expected = 15;
        assertEquals(expected, output);
	}

    @Test
	public void shouldGoAToB() {
		Day18 d = new Day18();
        List<Integer> keys = new ArrayList<Integer>();
        keys.addAll(List.of(90, 133));
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        long output = d.processLine(keys);

        int expected = 44;
        assertEquals(expected, output);
	}




}
