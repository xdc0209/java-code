package com.xdc.basic.api.mybatis.manual.model;

public class Student
{
    private Integer id;

    private String  name;

    private String  gender;

    private String  major;

    private String  grade;

    public Student()
    {
        super();
    }

    public Student(Integer id, String name, String gender, String major, String grade)
    {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.grade = grade;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name == null ? null : name.trim();
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major == null ? null : major.trim();
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade == null ? null : grade.trim();
    }

    @Override
    public String toString()
    {
        return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", major=" + major + ", grade=" + grade
                + "]";
    }
}