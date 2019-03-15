package org.nico.yasso.utils;

public class StringUtils {

    public static boolean isNotBlank(String str) {
        return str != null && ! str.isEmpty();
    }
    
    public static boolean isBlank(String str) {
        return ! isNotBlank(str);
    }
}
