package com.xdc.basic.api.sort;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Student implements Comparable<Student>
{
    private String name;
    private int    score;
    private int    age;

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

    // Two Objects that compare equal using equals(Object) should normally also
    // compare equal using compareTo(Object).
    // 先比较年龄，再比较姓名，再比较是否抽烟
    @Override
    public int compareTo(Student o)
    {
        // 用不用判断null呢？
        Student that = o;
        return new CompareToBuilder().append(this.score, that.score).append(this.age, that.age).toComparison();
    }

    // Two Objects that compare equal using equals(Object) should normally also
    // compare equal using compareTo(Object).
    // 不推荐。由于该类涉及到比较字段的顺序，所以并不推荐使用该族中的方法自动映射字段，而最好自己决定那些字段参与比较及它们的顺序。该族方法的返回值为一整数，意义同Comparable接口的compareTo方法。
    public int compareTo2(Student o)
    {
        return CompareToBuilder.reflectionCompare(this, o);
    }
}
