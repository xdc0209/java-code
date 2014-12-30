package com.xdc.basic.api.apache.commons.lang3.builder;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PersonReflectionToStringBuilder
{
    String  name;
    int     age;
    boolean smoker;

    public PersonReflectionToStringBuilder(String name, int age, boolean smoker)
    {
        super();
        this.name = name;
        this.age = age;
        this.smoker = smoker;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String toString2()
    {
        // 这个排除某些字段，但是不能指定风格
        return ReflectionToStringBuilder.toStringExclude(this, new String[] { "age", "smoker" });
    }

    public String toString3()
    {
        // 这个排除某些字段，又指定风格
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.setExcludeFieldNames(new String[] { "age", "smoker" });
        return builder.toString();
    }
}
