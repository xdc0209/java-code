package com.xdc.basic.commons.to.string.style;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

import com.xdc.basic.commons.codec.BytesUtil;

public class EnhanceToStringStyleTest
{
    @Test
    public void testConfigBytesToStringCharset() throws IOException
    {
        System.out.println(
                ReflectionToStringBuilder.toString(new Object1(), EnhanceToStringStyle.newMultiLineToStringStyle()));

        System.out.println(ReflectionToStringBuilder.toString(new Object1(),
                EnhanceToStringStyle.newMultiLineToStringStyle().configBytesToStringCharset(null)));

        System.out.println(ReflectionToStringBuilder.toString(new Object1(),
                EnhanceToStringStyle.newMultiLineToStringStyle().configBytesToStringCharset(Charsets.UTF_8)));
    }

    @Test
    public void testConfigExcludeMapKeys()
    {
        System.out.println(
                ReflectionToStringBuilder.toString(new Object1(), EnhanceToStringStyle.newMultiLineToStringStyle()));

        System.out.println(ReflectionToStringBuilder.toString(new Object1(),
                EnhanceToStringStyle.newMultiLineToStringStyle().configExcludeMapKeys("password")));
    }

    @Test
    public void testConfigRecursiveReflectionSubobject()
    {
        System.out.println(
                ReflectionToStringBuilder.toString(new Object1(), EnhanceToStringStyle.newMultiLineToStringStyle()));

        System.out.println(ReflectionToStringBuilder.toString(new Object1(),
                EnhanceToStringStyle.newMultiLineToStringStyle().configRecursiveReflectionSubobject(false)));
    }
}

class Object1
{
    Object2              object2     = null;
    List<Object2>        object2List = new ArrayList<Object2>();
    Map<String, Object2> object2Map  = new HashMap<String, Object2>();

    public Object1()
    {
        super();

        object2 = new Object2();

        this.object2List.add(new Object2());
        this.object2List.add(new Object2());

        this.object2Map.put("user", new Object2());
        this.object2Map.put("password", new Object2());
    }
}

class Object2
{
    int     int1     = 10;
    Integer integer2 = 20;
    String  string3  = "30";
    byte[]  bytes4   = BytesUtil.getBytes("hahaha", Charsets.UTF_8);
}
