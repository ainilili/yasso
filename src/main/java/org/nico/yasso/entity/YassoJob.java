package org.nico.yasso.entity;

import org.nico.yasso.Yasso;
import org.nico.yasso.consts.BuildState;
import org.nico.yasso.plugins.AbstractPlugins;
import org.nico.yasso.plugins.GitPlugins;
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
    
    private Mail mail;

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
        workspace = Yasso.getYasso().getWorkspace();
        context = new Context();
        
        plugins.initialize();
        plugins.check(this);
        
        
        context.put("workspace", workspace);
        context.put("jobspace", jobspace);
        context.put("jobName", name);
        
        if(git != null) {
            context.put("projectName", git.getName());
            context.put("branch", git.getBranch());
        }
        
        context.put("buildState", BuildState.FAILURE);
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

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
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
