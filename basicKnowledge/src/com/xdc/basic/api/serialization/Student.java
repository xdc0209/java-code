package com.xdc.basic.api.serialization;

import java.io.Serializable;

public class Student implements Serializable
{
    //	序列化标志
    //	Add generated serial version ID
    //	添加已生成的串行版本标识

    private static final long serialVersionUID = -5104809987465264163L;

    private String            name;
    private int               age;

    public Student(String name, int age)
    {
        super();
        this.name = name;
        this.age = age;
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

    @Override
    public String toString()
    {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}
