package com.xdc.basic.api.json.jsonsmart;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Student
{
    private String       name;
    private int          score;
    private int          age;
    private List<String> goodFriends;

    public Student()
    {
        super();
    }

    public Student(String name, int score, int age)
    {
        super();
        this.setName(name);
        this.setScore(score);
        this.setAge(age);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public List<String> getGoodFriends()
    {
        return goodFriends;
    }

    public void setGoodFriends(List<String> goodFriends)
    {
        this.goodFriends = goodFriends;
    }

}
