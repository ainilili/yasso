package org.nico.yasso.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandUtils.class);

    public static Result execute(String script, String dir) {
        try {
            LOGGER.info(">>>>>> + {}", script);
            Process process = Runtime.getRuntime().exec(script, null, new File(dir));
            Result result = doWaitFor(process);
            LOGGER.info("<<<<<< - {}", result.isSuccessed() ? "SUCCESS" : "FAILED");
            
            return result;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result[] execute(String[] scripts, String dir) {
        Result[] results = new Result[scripts.length];
        int index = 0;
        for(String script: scripts) {
            results[index ++] = execute(script, dir);
        }
        return results;  
    }

    //    public static String readStream(InputStream inputStream) throws IOException {
    //        StringBuffer sb = new StringBuffer(); 
    //        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK")); 
    //        String line = null; 
    //        while ((line = br.readLine()) != null) { 
    //            sb.append(line).append("\n"); 
    //            LOGGER.info(line);
    //        }
    //        return sb.toString();
    //    }

    //    @SuppressWarnings("static-access")
    //    public static Result doWaitFor(Process process) {
    //        InputStream in = null;
    //        InputStream err = null;
    //
    //        String inMsg = null;
    //        String errMsg = null;
    //
    //        try {
    //            in = process.getInputStream();
    //            err = process.getErrorStream();
    //
    //            boolean finished = false; // Set to true when p is finished
    //            while (!finished) {
    //                try {
    //                    System.out.println("read...");
    //                    inMsg = readStream(in);
    //                    errMsg = readStream(err);
    //                    System.out.println("read... end");
    //                    // Ask the process for its exitValue. If the process
    //                    // is not finished, an IllegalThreadStateException
    //                    // is thrown. If it is finished, we fall through and
    //                    // the variable finished is set to true.
    //                    int value = process.exitValue();
    //                    System.out.println("value...：" + value);
    //                    finished = true;
    //                } catch (IllegalThreadStateException e) {
    //                    // Process is not finished yet;
    //                    // Sleep a little to save on CPU cycles
    //                    System.out.println("exec...：" + e.getMessage());
    //                    Thread.currentThread().sleep(500);
    //                }
    //            }
    //        } catch (Exception e) {
    //        } finally {
    //            try {
    //                if (in != null) {
    //                    in.close();
    //                }
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //            if (err != null) {
    //                try {
    //                    err.close();
    //                } catch (IOException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }
    //        return new Result(errMsg, inMsg);
    //    }

    public static Result doWaitFor(Process process) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = 0;

        StringBuilder inMsg = new StringBuilder();
        StringBuilder errMsg = new StringBuilder();

        try {
            in = process.getInputStream();
            err = process.getErrorStream();

            boolean finished = false; // Set to true when p is finished
            while (!finished) {
                try {
                    StringBuilder line = new StringBuilder();
                    while (in.available() > 0) {
                        // Print the output of our system call
                        Character c = new Character((char) in.read());
                        inMsg.append(c);
                        if(c != '\n') {
                            line.append(c);
                        }else {
                            LOGGER.info(line.toString());
                            line.setLength(0);
                        }
                    }
                    while (err.available() > 0) {
                        // Print the output of our system call
                        Character c = new Character((char) err.read());
                        errMsg.append(c);
                        if(c != '\n') {
                            line.append(c);
                        }else {
                            LOGGER.info(line.toString());
                            line.setLength(0);
                        }
                    }
                    // Ask the process for its exitValue. If the process
                    // is not finished, an IllegalThreadStateException
                    // is thrown. If it is finished, we fall through and
                    // the variable finished is set to true.
                    exitValue = process.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    // Process is not finished yet;
                    // Sleep a little to save on CPU cycles
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Result(errMsg.toString(), inMsg.toString(), exitValue == 0);
    }

    public static class Result{

        private String errorMsg;

        private String successMsg;
        
        private boolean successed;

        public Result(String errorMsg, String successMsg, boolean successed) {
            this.errorMsg = errorMsg;
            this.successMsg = successMsg;
            this.successed = successed;
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

        public boolean isSuccessed() {
            return successed;
        }

        public void setSuccessed(boolean successed) {
            this.successed = successed;
        }

        @Override
        public String toString() {
            return "Result [errorMsg=" + errorMsg + ", successMsg=" + successMsg + ", successed=" + successed + "]";
        }

    }
}
