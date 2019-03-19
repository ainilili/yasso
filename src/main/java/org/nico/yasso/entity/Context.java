package org.nico.yasso.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context{
    
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
