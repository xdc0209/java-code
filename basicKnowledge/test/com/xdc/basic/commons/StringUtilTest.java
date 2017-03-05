package com.xdc.basic.commons;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest
{
    @Test
    public void testMatches()
    {
        Assert.assertEquals(false, StringUtil.matches(null, "^$"));
        Assert.assertEquals(false, StringUtil.matches(null, "^[a-zA-Z]+$"));

        Assert.assertEquals(true, StringUtil.matches("", "^$"));
        Assert.assertEquals(false, StringUtil.matches("", "^[a-zA-Z]+$"));

        Assert.assertEquals(false, StringUtil.matches("    ", "^$"));
        Assert.assertEquals(false, StringUtil.matches("    ", "^[a-zA-Z]+$"));

        Assert.assertEquals(false, StringUtil.matches("hehe", "^$"));
        Assert.assertEquals(true, StringUtil.matches("hehe", "^[a-zA-Z]+$"));
    }
}
