package com.xdc.basic.api.rmi.sbus.api;

import com.xdc.basic.api.apache.commons.lang3.builder.Person;
import com.xdc.basic.api.json.jsonsmart.Student;

public interface HelloService
{
    public String sayHello(String name);

    public Student newPerson(String name);

    public Student newPerson(String name, Student s, Person person) throws Throwable;
}
