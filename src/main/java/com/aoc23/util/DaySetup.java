package com.aoc23.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import static com.aoc23.util.FileUtils.*;


public class DaySetup {

    static String day = "6";
    static String template = "aoc23\\src\\main\\resources";
    static String destDay = "\\java\\com\\aoc23\\y2023\\";
    static String testFolder = "aoc23\\src\\main\\resources\\day1";

    public static void main(String[] args0){
        //copyTestFiles();
        createDay();
    }
    
    private static void createDay(){
        String fileTemplate = getFileString(template, "\\DayX.java");
        fileTemplate.replaceAll("DAY_NUMBER", day);
        writeFile(destDay,"Day"+day+".java", fileTemplate);

    }

    private static void copyTestFiles(){
        String testFolder = getFileString(template);

    }



    private static void writeFile(String dir, String fileName, String input){
		try {
			Files.writeString(getFilePath(dir, fileName),
			input, 
			StandardOpenOption.CREATE,
			StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
