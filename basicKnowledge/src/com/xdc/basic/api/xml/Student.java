package com.xdc.basic.api.xml;

public class Student
{
    private String id;
    private String name;
    private String age;
    private String sex;
    private String address;

    public Student()
    {
        super();
    }

    public Student(String id, String name, String age, String sex, String address)
    {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return String.format("Student [id=%s, name=%s, age=%s, sex=%s, address=%s]", id, name, age, sex, address);
    }
}
