package com.xdc.basic.api.rmi.principle.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.xdc.basic.api.rmi.principle.intf.Person;

public class PersonStub implements Person
{
    private final Socket socket;

    public PersonStub() throws Throwable
    {
        // connect to skeleton      
        socket = new Socket("127.0.0.1", 9999);
    }

    @Override
    public int getAge() throws Throwable
    {
        // pass method name to skeleton      
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("age");
        outStream.flush();
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        return inStream.readInt();
    }

    @Override
    public String getName() throws Throwable
    {
        // pass method name to skeleton      
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("name");
        outStream.flush();
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        return (String) inStream.readObject();
    }
}