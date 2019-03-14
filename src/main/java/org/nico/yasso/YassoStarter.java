package org.nico.yasso;

import java.io.IOException;

import org.nico.yasso.exceptions.YassoException;

public class YassoStarter {

    public static void main(String[] args) throws IOException, YassoException {
        
        String conf = "yasso-conf.yml";
        
        if(args != null && args.length > 0 && args.length % 2 == 0) {
            for(int index = 0; index < args.length; index = index + 2) {
                if(args[index].equals("-c")) {
                    conf = args[index + 1];
                }
            }
        }
        
        Yasso.getInstance(conf);
        
    }
}
