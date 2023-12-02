package util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static com.aoc23.util.FileUtils.*;


public class UtilsTest {
    @Test
	public void shouldGetFilePath() {
		String fileName = "someFile";
        String expected ="src\\main\\resources\\"+ fileName;
		String retString = getFilePath(fileName).toString();
		assertEquals(expected, retString);
	}


	 @Test
	public void shouldReadFileReturnString() {
		String fileName = "test.txt";
		String expected ="test file here";

		writeFile(fileName, expected);

		String retString = getFileString(fileName);
		deleteFile(fileName);

		assertEquals(expected, retString);

		
	}

	@Test
	public void shouldReadFileReturnList() {
		String fileName = "test.txt";
		String input ="test file here"+System.lineSeparator()+"another line";
				
		writeFile(fileName, input);

		List<String> retString = getFileLinesList(fileName);

		deleteFile(fileName);

		List<String> expected = Arrays.asList(input.split(System.lineSeparator()));
		assertEquals(expected, retString);

		
	}


	private static void writeFile(String fileName, String input){
		try {
			Files.writeString(getFilePath(fileName),
			input, 
			StandardOpenOption.CREATE,
			StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	



	private static void deleteFile(String fileName){
		try{
			Files.delete(getFilePath(fileName));
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
