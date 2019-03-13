package org.nico.yasso.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nico.yasso.Yasso;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;

public class YassoJob {

    private String name;
    
    private String workspace;
    
    private String jobspace;

    private Git git;
    
    private Build build;
    
    private Context context;

    private volatile boolean scriptBuildFlag;
    
    public void initialize() {
        String gitUser = git.getUser();
        String gitPwd = git.getPwd();
        String gitUrl = git.getUrl();
        String projectName = null;
        
        if(StringUtils.isNotBlank(gitUser)
                && StringUtils.isNotBlank(gitPwd)) {
            String sign = gitUser + ":" + gitPwd + "@";
            int httpFlag = gitUrl.indexOf("://");
            httpFlag += 3;
            gitUrl = gitUrl.substring(0, httpFlag) + sign + gitUrl.substring(httpFlag);
        }

        projectName = gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf("."));
        
        git.setName(projectName);
        git.setUrl(gitUrl);
        
        context = new Context();
        
        workspace = Yasso.getYasso().getWorkspace();
        jobspace = FileUtils.combination(workspace, projectName);
    }

    public boolean isScriptBuildFlag() {
        return scriptBuildFlag;
    }

    public void setScriptBuildFlag(boolean scriptBuildFlag) {
        this.scriptBuildFlag = scriptBuildFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Git getGit() {
        return git;
    }

    public void setGit(Git git) {
        this.git = git;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getJobspace() {
        return jobspace;
    }

    public void setJobspace(String jobspace) {
        this.jobspace = jobspace;
    }

    public static class Git{
        
        private String url;
        
        private String user;
        
        private String pwd;
        
        private String name;
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
        
    }
    
    public static class Build {
        
        private String cron;
        
        private String pre;
        
        private String post;

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getPre() {
            return pre;
        }

        public void setPre(String pre) {
            this.pre = pre;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }
        
    }
    
    public static class Context{
        
        private Map<String, Object> datas;
        
        public Context() {
            datas = new ConcurrentHashMap<String, Object>();
        }
        
        public Object put(String key, Object value) {
            return datas.put(key, value);
        }
        
        public Object remove(String key, Object value) {
            return datas.remove(key, value);
        }
        
        public boolean contains(String key) {
            return datas.containsKey(key);
        }

        public Map<String, Object> getDatas() {
            return datas;
        }

        public void setDatas(Map<String, Object> datas) {
            this.datas = datas;
        }

    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        YassoJob other = (YassoJob) obj;
        return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
