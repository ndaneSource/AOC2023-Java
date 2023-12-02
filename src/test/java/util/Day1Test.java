package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aoc23.y2023.Day1;

public class Day1Test {

    @Test
	public void shouldFindNumberString() {
		Day1 d = new Day1();
        String test = d.convertNumString("twotwo");

        System.out.println(test);
        assertEquals("2two2", test);
	}

    @Test
	public void shouldFindNumberStringBuried() {
		Day1 d = new Day1();
        String test = d.convertNumString("ttwotyeitwokd");

        System.out.println(test);
        assertEquals("2tyei2", test);
	}

    @Test
	public void shouldFindNumberSubString() {
		Day1 d = new Day1();
        String test = d.convertNumSubString("two");

        System.out.println(test);
        assertEquals(test, "2");

		
	}

     @Test
	public void shouldReturnInput() {
		Day1 d = new Day1();
        String test = d.convertNumSubString("notanumber");

        System.out.println(test);
        assertEquals(test, "notanumber");

	}
}
