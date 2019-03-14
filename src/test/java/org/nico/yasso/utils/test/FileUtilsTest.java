package org.nico.yasso.utils.test;

import org.junit.Assert;
import org.junit.Test;
import org.nico.yasso.utils.FileUtils;

public class FileUtilsTest {

    boolean isWin = System.getProperty("os.name").toLowerCase().startsWith("win");
    
    @Test
    public void parseNameTest() {
        if(isWin) {
            Assert.assertEquals(FileUtils.parseFileName("C:\\test.yml"), "test");
            Assert.assertEquals(FileUtils.parseFileName("C:\\abc\\test.yml"), "test");
        }else {
            Assert.assertEquals(FileUtils.parseFileName("/abc/test.yml"), "test");
        }
        Assert.assertEquals(FileUtils.parseFileName("test.yml"), "test");
    }
    
    @Test
    public void containsFileTest() {
        System.out.println(FileUtils.containsFile("D:\\yasso", "snails.yml"));
        System.out.println(FileUtils.containsFile("D:\\yasso", "test"));
    }
    
    @Test
    public void isRelative() {
        Assert.assertFalse(FileUtils.isRelative("C:\\abc"));
        Assert.assertFalse(FileUtils.isRelative("/abc"));
        Assert.assertTrue(FileUtils.isRelative("abc"));
    }
    
    @Test
    public void combination() {
        if(isWin) {
            Assert.assertEquals(FileUtils.combination("abc\\", "\\bce"), "abc\\bce");
            Assert.assertEquals(FileUtils.combination("abc", "\\bce"), "abc\\bce");
            Assert.assertEquals(FileUtils.combination("abc", "bce"), "abc\\bce");
            Assert.assertEquals(FileUtils.combination("abc\\", "bce"), "abc\\bce");
        }else {
            Assert.assertEquals(FileUtils.combination("abc/", "/bce"), "abc/bce");
            Assert.assertEquals(FileUtils.combination("abc/", "bce"), "abc/bce");
            Assert.assertEquals(FileUtils.combination("abc", "/bce"), "abc/bce");
            Assert.assertEquals(FileUtils.combination("abc", "bce"), "abc/bce");
        }
    }
}
