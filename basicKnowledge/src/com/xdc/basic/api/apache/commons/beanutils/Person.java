package com.xdc.basic.api.apache.commons.beanutils;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person
{
    String  name;
    int     age;
    boolean smoker;
    Date    birth;

    public Person()
    {
    }

    public Person(String name, int age, boolean smoker, Date birth)
    {
        super();
        this.name = name;
        this.age = age;
        this.smoker = smoker;
        this.birth = birth;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean isSmoker()
    {
        return smoker;
    }

    public void setSmoker(boolean smoker)
    {
        this.smoker = smoker;
    }

    public Date getBirth()
    {
        return birth;
    }

    public void setBirth(Date birth)
    {
        this.birth = birth;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
