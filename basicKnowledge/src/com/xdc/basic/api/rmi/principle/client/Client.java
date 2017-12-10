package com.xdc.basic.api.rmi.principle.client;

import com.xdc.basic.api.rmi.principle.intf.Person;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            Person person = new PersonStub();
            int age = person.getAge();
            String name = person.getName();
            System.out.println(name + " is " + age + " years old.");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
