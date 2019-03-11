package org.nico.yasso.pipeline.jobs;

public class YassoJob {

    private String gitUrl;
    
    private String gitUser;
    
    private String gitPwd;
    
    private String preShell;
    
    private String postShell;
    
    private String cron;

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getGitUser() {
        return gitUser;
    }

    public void setGitUser(String gitUser) {
        this.gitUser = gitUser;
    }

    public String getGitPwd() {
        return gitPwd;
    }

    public void setGitPwd(String gitPwd) {
        this.gitPwd = gitPwd;
    }

    public String getPreShell() {
        return preShell;
    }

    public void setPreShell(String preShell) {
        this.preShell = preShell;
    }

    public String getPostShell() {
        return postShell;
    }

    public void setPostShell(String postShell) {
        this.postShell = postShell;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
    
}
