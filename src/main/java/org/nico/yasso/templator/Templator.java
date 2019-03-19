package org.nico.yasso.templator;

import java.io.FileNotFoundException;
import java.util.Map;

import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Templator {

    private String prefix;
    
    private String suffix;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Templator.class);

    public Templator(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String render(String template, Map<String, Object> datas) {
        if(StringUtils.isBlank(prefix) || StringUtils.isBlank(suffix)) {
            throw new NullPointerException("prefix or suffix can't be null !");
        }
        
        if(StringUtils.isBlank(template)) {
            return "";
        }
        return render(template, 0, datas);
    }
    
    private String render(String template, int start, Map<String, Object> datas) {
        int startIndex = template.indexOf(prefix, start);
        if(startIndex != -1) {
            int endIndex = template.indexOf(suffix, startIndex);
            if(endIndex != -1) {
                endIndex += suffix.length();
                template = template.substring(0, startIndex) + replace(template.substring(startIndex, endIndex), datas) + template.substring(endIndex);
                return render(template, startIndex, datas);
            }
        }
        return template;
    }
    
    protected String replace(String fn, Map<String, Object> datas) {
        String value = fn.substring(prefix.length(), fn.length() - suffix.length());
        String[] splitValues = value.split("\\,");
        
        if(splitValues.length == 1) {
            if(datas.containsKey(value)) {
                return datas.get(value).toString();
            }
        }else {
            String key = splitValues[0];
            String target = splitValues[1];
            
            //Extended parsing here
            if(key.equalsIgnoreCase("FILE")) {
                try {
                    return FileUtils.read2Str(FileUtils.combination(datas.get("jobspace").toString(), target));
                } catch (FileNotFoundException e) {
                    LOGGER.error("Template file parsing error: {}", e.getMessage());
                }
            }else {
                LOGGER.error("Undefined template: {}", fn);
            }
        }
        return fn;
    }
}
