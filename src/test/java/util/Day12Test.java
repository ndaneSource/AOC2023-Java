package util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import com.aoc23.y2023.Day12;

public class Day12Test {
    @Test
	public void shouldFindSingleHashReturn1() {
		Day12 d = new Day12();
        String puzzle = "#";
        List<Integer> keys = List.of(1);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 1;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindQsReturn1() {
		Day12 d = new Day12();
        String puzzle = "?#?.?";
        List<Integer> keys = List.of(1);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 1;
        assertEquals(expected, output);
	}

    @Test
	public void shouldReturn2SingleKey() {
		Day12 d = new Day12();
        String puzzle = "?#?.?";
        List<Integer> keys = List.of(2);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 2;
        assertEquals(expected, output);
	}

    @Test
	public void shoulddReturn2MultiKey() {
		Day12 d = new Day12();
        String puzzle = "?#?.?";
        List<Integer> keys = List.of(2,1);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 2;
        assertEquals(expected, output);
	}

    @Test
	public void shouldProcessFirstLineTest() {
		Day12 d = new Day12();
        String puzzle = "???.###";
        List<Integer> keys = List.of(1,1,3);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 1;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindExtraHashReturnNeg() {
		Day12 d = new Day12();
        String puzzle = "?#?.#?";
        List<Integer> keys = List.of(1);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 0;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindOneMatch() {
		Day12 d = new Day12();
        String puzzle = "???.###";
        List<Integer> keys = List.of(1,1,3);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 1;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindRealSolution2() {
		Day12 d = new Day12();
        String puzzle = "?###????????";
        List<Integer> keys = List.of(3,2,1);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 10;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindRealSolution3() {
		Day12 d = new Day12();
        String puzzle = ".??..??...?##.";
        List<Integer> keys = List.of(1,1,3);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int output = d.simplify(puzzle, keys);

        int expected = 4;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindRealSolution4() {
		Day12 d = new Day12();
        int output = d.solution1(List.of("???????.??????? 1,1,1,2,1"));
        //List<String> puzzleList = d.splitPuzzle(puzzle);

        int expected = 160;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindRealSolution5() {
		Day12 d = new Day12();
        int output = d.solution1(List.of("??.????#??? 1,1,4"));
        //List<String> puzzleList = d.splitPuzzle(puzzle);

        int expected = 13;
        assertEquals(expected, output);
	}

    @Test
	public void shouldFindRealSolution6() {
		Day12 d = new Day12();
        int output = d.solution1(List.of("?.???????.#?????#??? 1,1,3,7,1"));
        //List<String> puzzleList = d.splitPuzzle(puzzle);

        int expected = 8;
        assertEquals(expected, output);
	}
    
    @Test
	public void shouldReturnPuzzleMetrics() {
		Day12 d = new Day12();
        String puzzle = "??.###";
        List<Integer> keys = List.of(1, 3);
        //List<String> puzzleList = d.splitPuzzle(puzzle);
        int[] output = d.getPuzzleMetrics(puzzle, keys);

        int[] expected = new int[] {4,3, 2,0,0};
        assertEquals(expected[2], output[2]);
        assertEquals(expected[1], output[1]);
        assertEquals(expected[0], output[0]);
	}


}
