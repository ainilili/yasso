package org.nico.yasso.entity;

import java.util.List;

import org.simplejavamail.mailer.config.TransportStrategy;

public class Mail{
    
    private Config config;
    
    private Template template;
    
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Config{
        
        private String smtp;
        
        private int port;
        
        private String user;
        
        private String pwd;
        
        private TransportStrategy strategy = TransportStrategy.SMTP;
        
        private int timeout = 10 * 1000;
        
        private boolean debug = false;

        public String getSmtp() {
            return smtp;
        }

        public void setSmtp(String smtp) {
            this.smtp = smtp;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
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

        public TransportStrategy getStrategy() {
            return strategy;
        }

        public void setStrategy(TransportStrategy strategy) {
            this.strategy = strategy;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }
        
    }
    
    public static class Template{
        
        private String addresser;
        
        private List<String> recipients;
        
        private String title;
        
        private String type = "txt";
        
        private String content;

        public String getAddresser() {
            return addresser;
        }

        public void setAddresser(String addresser) {
            this.addresser = addresser;
        }

        public List<String> getRecipients() {
            return recipients;
        }

        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
        
    }
}
