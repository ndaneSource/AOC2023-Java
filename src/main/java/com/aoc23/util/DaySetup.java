package com.aoc23.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import static com.aoc23.util.FileUtils.*;


public class DaySetup {

    static String day = "21";

    static String baseDir = "aoc23\\src\\main\\";
    static String template = "aoc23\\src\\main\\resources";
    static String destDay = "java\\com\\aoc23\\y2023\\";
    static String testFolders = "\\day";

    public static void main(String[] args0){
        copyTestFiles();
        createDay();
    }
    
    private static void createDay(){
        String fileTemplate = getFileString(template, "\\DayX.java");
        fileTemplate = fileTemplate.replaceAll("DAY_NUMBER", day);
        writeFile(baseDir+destDay,"Day"+day+".java", fileTemplate);

    }

    private static void copyTestFiles(){
        try{
            Files.copy(getFilePath(testFolders+"1"), getFilePath(testFolders+day));
            Files.walk(getFilePath(testFolders+1))
                .filter(f -> f.getFileName().toString().endsWith("txt"))
                .forEach(f -> {
                    try {
                        Files.copy(f, getFilePath(testFolders+day+"\\"+f.getFileName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            
        } catch (IOException e) {
			e.printStackTrace();
		}

    }



    private static void writeFile(String dir, String fileName, String input){
		try {
			Files.writeString(getFilePath(dir, fileName),
                input, 
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
