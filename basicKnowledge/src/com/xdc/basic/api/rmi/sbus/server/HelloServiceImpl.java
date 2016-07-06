package com.xdc.basic.api.rmi.sbus.server;

import com.xdc.basic.api.apache.commons.lang3.builder.Person;
import com.xdc.basic.api.json.jsonsmart.Student;
import com.xdc.basic.api.rmi.sbus.api.HelloService;

public class HelloServiceImpl implements HelloService
{
    @Override
    public String sayHello(String name)
    {
        return "Hello " + name + ".";
    }

    @Override
    public Student newPerson(String name)
    {
        return new Student(name, 11, 12);
    }

    @Override
    public Student newPerson(String name, Student s, Person person) throws Exception
    {
        if (name.equals("xdc"))
        {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("xxxxxxx");

            throw new Exception("xxssxss", illegalArgumentException);
        }
        System.out.println(s);
        System.out.println(person);
        return new Student(name, 11, 12);
    }
}
