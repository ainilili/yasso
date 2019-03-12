package org.nico.yasso.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.nico.yasso.pipeline.impl.GitPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitPipeline.class);
    
    public static Result execute(String script, String dir) {
        try {
            LOGGER.info("+ " + script);
            Process process = Runtime.getRuntime().exec(script, null, new File(dir));
            return getResult(process);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result[] execute(String[] scripts, String dir) {
        Result[] results = new Result[scripts.length];
        int index = 0;
        for(String script: scripts) {
            LOGGER.info("+ " + script);
            results[index ++] = execute(script, dir);
        }
        return results;  
    }

    private static Result getResult(Process process) throws InterruptedException, IOException {
        process.waitFor();
        InputStream errorStream = process.getErrorStream();
        InputStream successStream = process.getInputStream();
        return new Result(readStream(errorStream), readStream(successStream)); 

    }  

    public static String readStream(InputStream inputStream) throws IOException {
        StringBuffer sb = new StringBuffer(); 
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK")); 
        String line = null; 
        while ((line = br.readLine()) != null) { 
            sb.append(line).append("\n"); 
        }
        return sb.toString();
    }

    public static class Result{

        private String errorMsg;

        private String successMsg;

        public Result(String errorMsg, String successMsg) {
            this.errorMsg = errorMsg;
            this.successMsg = successMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getSuccessMsg() {
            return successMsg;
        }

        public void setSuccessMsg(String successMsg) {
            this.successMsg = successMsg;
        }

        @Override
        public String toString() {
            return "Result [errorMsg=" + errorMsg + "\n, successMsg=" + successMsg + "]";
        }

    }
}
