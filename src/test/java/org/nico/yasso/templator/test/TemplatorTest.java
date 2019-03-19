package org.nico.yasso.templator.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nico.yasso.templator.Templator;

public class TemplatorTest {

    @Test
    public void testTemplator(){
        
        String teststr = "${e}abc${a}${b}de${c}fghigklmn${d}";
        
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("a", 1);
        datas.put("b", 2);
        datas.put("c", 3);
        datas.put("d", 4);
        datas.put("e", 5);
        
        Templator tor = new Templator("${", "}");
        String result = tor.render(teststr, datas);
        System.out.println(result);
    }
}
