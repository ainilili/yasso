package org.nico.yasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.nico.yasso.config.YassoConfiguration;
import org.yaml.snakeyaml.Yaml;

public class Yasso {

    public static void main(String[] args) throws FileNotFoundException {
        
        
        
        YassoConfiguration configuration = YassoConfiguration.getInstance("yasso-conf.yml");
        System.out.println(configuration.getYassoHome());
        System.out.println(configuration.getJobsHome());
        
        
        
    }
}
