package org.nico.yasso.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static final String separator = File.separator;
    
    public static String parseFileName(String file) {
        int startIndex = file.lastIndexOf(separator);
        int endIndex = file.lastIndexOf(".");
        startIndex += separator.length();
        if(endIndex < 0) endIndex = file.length();
        return file.substring(startIndex, endIndex);
    }
    
    public static boolean containsFile(String dirPath, String fileName) {
        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory()) {
            return dir.listFiles((d, name) -> name.equalsIgnoreCase(fileName)).length > 0;
        }
        return false;
    }
    
    public static void createDirIfAbsent(String dir) {
        File targetDir = new File(dir);
        if(! targetDir.exists() || ! targetDir.isDirectory()) {
            targetDir.mkdir();
        }
    }
    
    public static File createFile(String filePath) {
        File file = new File(filePath);
        if(! file.exists()) {
            try {
                file.createNewFile();
                file.setReadable(true, false);
                file.setExecutable(true, false);
                file.setWritable(true, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    public static void createFileAndWrite(String filePath, String content) {
        File file = createFile(filePath);
        if(file.exists()) {
            write(file, content);
        }
    }
    
    public static void write(File file, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);    
            writer.write(content);
            writer.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static boolean isRelative(String file) {
        if(file.startsWith("/")) return false;
        if(file.matches("[a-zA-Z]:\\\\(.*)")) return false;
        return true;
    }
    
    public static String combination(String pre, String after) {
        if(after.startsWith(separator)) {
            after = after.substring(separator.length());
        }
        if(! pre.endsWith(separator)) {
            pre += separator;
        }
        return pre + after;
    }
    
    public static boolean isYaml(String fileName) {
        return fileName.endsWith(".yml") || fileName.endsWith(".yaml");
    }
    
}
