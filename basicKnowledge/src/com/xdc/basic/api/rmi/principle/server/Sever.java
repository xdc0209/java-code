package com.xdc.basic.api.rmi.principle.server;

import com.xdc.basic.api.rmi.principle.intf.Person;

public class Sever
{
    public static void main(String args[])
    {
        // new object server
        Person person = new PersonImpl("Richard", 34);
        PersonSkeleton skel = new PersonSkeleton(person);
        skel.start();
    }
}
