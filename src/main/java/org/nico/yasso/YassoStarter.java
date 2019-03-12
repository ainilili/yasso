package org.nico.yasso;

import java.io.IOException;

public class YassoStarter {

    public static void main(String[] args) throws IOException {
        
        Yasso.getInstance("yasso-conf.yml");
        
    }
}
