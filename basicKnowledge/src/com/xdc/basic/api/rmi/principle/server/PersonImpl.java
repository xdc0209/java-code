package com.xdc.basic.api.rmi.principle.server;

import com.xdc.basic.api.rmi.principle.intf.Person;

public class PersonImpl implements Person
{
    private final int    age;
    private final String name;

    public PersonImpl(String name, int age)
    {
        this.age = age;
        this.name = name;
    }

    @Override
    public int getAge()
    {
        return age;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
