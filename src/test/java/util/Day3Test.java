package util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.aoc23.y2023.Day3;

public class Day3Test {

    @Test
	public void shouldFindNumberString() {
		Day3 d = new Day3();
        String line = "..**..";
        List<Integer> test = d.getSymbols(line);
        System.out.println(test);
        assertEquals(List.of(2,3), test);
	}

}
