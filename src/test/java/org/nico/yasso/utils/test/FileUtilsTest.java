package org.nico.yasso.utils.test;

import org.junit.Assert;
import org.junit.Test;
import org.nico.yasso.utils.FileUtils;

public class FileUtilsTest {

    @Test
    public void parseNameTest() {
        String name = FileUtils.parseName("test.yml");
        System.out.println(name);
        Assert.assertEquals(name, "test");
    }
    
    @Test
    public void containsFileTest() {
        System.out.println(FileUtils.containsFile("D:\\yasso", "snails.yml"));
        System.out.println(FileUtils.containsFile("D:\\yasso", "test"));
    }
}
