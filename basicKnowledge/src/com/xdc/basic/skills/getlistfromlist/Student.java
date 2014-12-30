package com.xdc.basic.skills.getlistfromlist;

public class Student
{
    private String number;

    private String name;

    public Student(String number, String name)
    {
        super();
        this.number = number;
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
