package org.nico.yasso.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

public class YamlUtils {

    private static Yaml yaml = new Yaml();
    
    public static <T> T loadAs(String filePath, Class<T> clazz) throws IOException {
        FileInputStream inputStream = null;
        T result = null;
        try {
            File file = new File(filePath);
            if(file.exists()) {
                result = yaml.loadAs(new FileInputStream(file), clazz);   
            }
            return result;
        }finally {
            if(inputStream != null) inputStream.close();
        }
    }
}
