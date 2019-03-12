package org.nico.yasso.utils;

public class FileUtils {

    public static String parseName(String file) {
        return file.substring(0, file.lastIndexOf("."));
    }
}
