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
    
    @Test
    public void isRelative() {
        Assert.assertFalse(FileUtils.isRelative("C://abc"));
        Assert.assertFalse(FileUtils.isRelative("/abc"));
        Assert.assertTrue(FileUtils.isRelative("abc"));
    }
    
    @Test
    public void combination() {
        Assert.assertEquals(FileUtils.combination("abc\\", "\\bce"), "abc\\bce");
        Assert.assertEquals(FileUtils.combination("abc", "\\bce"), "abc\\bce");
        Assert.assertEquals(FileUtils.combination("abc", "bce"), "abc\\bce");
        Assert.assertEquals(FileUtils.combination("abc\\", "bce"), "abc\\bce");
    }
}
