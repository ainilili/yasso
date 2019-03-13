package org.nico.yasso.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static String parseName(String file) {
        return file.substring(0, file.lastIndexOf("."));
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
        if(file.startsWith("/")) {
            return false;
        }
        if(file.matches("[a-zA-Z]://(.*)")) {
            return false;
        }
        return true;
    }
    
    public static String combination(String pre, String after) {
        if(after.startsWith(File.separator)) {
            after = after.substring(File.separator.length());
        }
        
        if(! pre.endsWith(File.separator)) {
            pre += File.separator;
        }
        return pre + after;
    }
}
