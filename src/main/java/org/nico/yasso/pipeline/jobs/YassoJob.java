package org.nico.yasso.pipeline.jobs;

import org.nico.yasso.utils.StringUtils;

public class YassoJob {

    private String name;

    private String gitUrl;

    private String projectName;

    private String gitUser;

    private String gitPwd;

    private String preShell;

    private String postShell;

    private String cron;

    public void init() {
        if(StringUtils.isNotBlank(gitUser)
                && StringUtils.isNotBlank(gitPwd)) {
            String sign = gitUser + ":" + gitPwd + "@";
            int httpFlag = gitUrl.indexOf("://");
            httpFlag += 3;
            gitUrl = gitUrl.substring(0, httpFlag) + sign + gitUrl.substring(httpFlag);
        }

        projectName = gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf("."));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        YassoJob other = (YassoJob) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
