package com.xdc.basic.api.apache.commons.lang3.builder;

public class PersonNotApache
{
    String  name;
    int     age;
    boolean smoker;

    public PersonNotApache(String name, int age, boolean smoker)
    {
        super();
        this.name = name;
        this.age = age;
        this.smoker = smoker;
    }

    // eclipse自动生成：字符串连接
    public String toString1()
    {
        return "PersonNotApache [name=" + name + ", age=" + age + ", smoker=" + smoker + "]";
    }

    // eclipse自动生成：StringBuilder/StringBuffer
    public String toString2()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonNotApache [name=");
        builder.append(name);
        builder.append(", age=");
        builder.append(age);
        builder.append(", smoker=");
        builder.append(smoker);
        builder.append("]");
        return builder.toString();
    }

    // eclipse自动生成：StringBuilder/StringBuffer - 链式调用
    public String toString3()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonNotApache [name=").append(name).append(", age=").append(age).append(", smoker=")
                .append(smoker).append("]");
        return builder.toString();
    }

    // eclipse自动生成：String.format/MessageFormat
    public String toString4()
    {
        return String.format("PersonNotApache [name=%s, age=%s, smoker=%s]", name, age, smoker);
    }
}
