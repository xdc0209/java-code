package com.xdc.basic.api.rmi.sbus.client;

import java.lang.reflect.Proxy;

import com.xdc.basic.api.apache.commons.lang3.builder.Person;
import com.xdc.basic.api.json.jsonsmart.Student;
import com.xdc.basic.api.rmi.sbus.api.HelloService;

public class Main
{
    public static void main(String[] args) throws Throwable
    {
        HelloService newProxyInstance = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
                new Class<?>[] { HelloService.class }, new SbusProxy());

        Student sayHello = newProxyInstance.newPerson("xdc", new Student("xdc2", 123, 11),
                new Person("xdc3", 12, false));
        System.out.println(sayHello);
    }
}
