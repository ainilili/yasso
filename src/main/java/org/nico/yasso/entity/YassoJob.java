package org.nico.yasso.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nico.yasso.Yasso;
import org.nico.yasso.plugins.AbstractPlugins;
import org.nico.yasso.plugins.GitPlugins;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;

public class YassoJob {

    private String pluginsClass;
    
    private String name;
    
    private String workspace;
    
    private String jobspace;
    
    private AbstractPlugins plugins;

    private Git git;
    
    private Build build;
    
    private Context context;

    private volatile boolean scriptBuildFlag;
    
    public YassoJob() {
    }

    public YassoJob(String name) {
        this.name = name;
    }

    public void initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if(StringUtils.isBlank(pluginsClass)) {
            plugins = new GitPlugins();
        }else {
            plugins = (AbstractPlugins) Class.forName(pluginsClass).newInstance();
        }
        
        context = new Context();
        workspace = Yasso.getYasso().getWorkspace();
        
        plugins.initialize();
        plugins.check(this);
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

    public String getPluginsClass() {
        return pluginsClass;
    }

    public void setPluginsClass(String pluginsClass) {
        this.pluginsClass = pluginsClass;
    }

    public AbstractPlugins getPlugins() {
        return plugins;
    }

    public void setPlugins(AbstractPlugins plugins) {
        this.plugins = plugins;
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
        
        private String branch;
        
        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

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
