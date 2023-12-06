package util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import com.aoc23.y2023.Day5;
import com.aoc23.y2023.Day5.Range;

public class Day5Test {

    @Test
	public void shouldGetCorrectRange() {
		Day5 d = new Day5();
        Range r = d.createRange("45 77 23");
        List<Pair<Long,Long>> output = d.getMatches(r, 45, 45);
        System.out.println(output);

        Pair<Long,Long> expected = new Pair<Long,Long>(77L,77L);
        assertEquals(expected, output.get(0));
	}

    @Test
	public void shouldGetCorrectRangeFirst() {
		Day5 d = new Day5();
        Range r = d.createRange("52 50 48");
        List<Pair<Long,Long>> output = d.getMatches(r, 84 , 84);
        System.out.println(output);

        Pair<Long,Long> expected = new Pair<Long,Long>(82L,82L);
        assertEquals(expected, output.get(0));
	}

    @Test
	public void shouldFindCorrectSeed() {
		Day5 d = new Day5();
        d.createSeedMap("seeds: 79 14 55 13");

        long output = d.findSeedMatch(82L, 92L);
        System.out.println(output);

        long expected = 82;
        assertEquals(expected, output);
	}



}
