package org.nico.yasso.utils;

import java.io.File;

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
}
