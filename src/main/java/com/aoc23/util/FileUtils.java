package com.aoc23.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileUtils {
    
    public static Path getFilePath(String fileName){
        return Paths.get("aoc23\\src\\main\\resources\\"+ fileName).toAbsolutePath();
    }

    public static Path getFilePath(String dir, String fileName){
        return Paths.get(dir+ fileName).toAbsolutePath();
    }

    public static List<String> getFileLinesList(String fileName){
        try{
            return Files.readAllLines(getFilePath(fileName));
        }catch(Exception e){
            System.out.println(e.toString());
            throw new Error(e);
        }
    }

    public static String getFileString(String fileName){
        try{
            return Files.readString(getFilePath(fileName));
        }catch(Exception e){
            System.out.println(e.toString());
            throw new Error(e);
        }
    }

    public static String getFileString(String dir, String fileName){
        try{
            return Files.readString(getFilePath(dir, fileName));
        }catch(Exception e){
            System.out.println(e.toString());
            throw new Error(e);
        }
    }


}