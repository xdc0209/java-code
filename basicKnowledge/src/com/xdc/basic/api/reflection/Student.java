package com.xdc.basic.api.reflection;

public class Student
{
    public String name;
    private int   age;

    public Student()
    {
        super();
    }

    @SuppressWarnings("unused")
    private Student(String name, int age)
    {
        super();
        this.name = name;
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public void study()
    {
        System.out.println("I study all day and night.");
    }

    @SuppressWarnings("unused")
    private void eat(String food)
    {
        System.out.println("I'm eating " + food + ". I'm a foodie.");
    }

    @Override
    public String toString()
    {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}
