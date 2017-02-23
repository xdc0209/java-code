package com.xdc.basic.api.rmi.principle.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.xdc.basic.api.rmi.principle.intf.Person;

public class PersonSkeleton extends Thread
{
    private final Person person;

    public PersonSkeleton(Person person)
    {
        // get reference of object server
        this.person = person;
    }

    @SuppressWarnings("resource")
    @Override
    public void run()
    {
        try
        {
            // new socket at port 9999
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("服务运行中...");
            System.out.println("服务端口号为：" + 9999);
            // accept stub's request
            Socket socket = serverSocket.accept();
            while (socket != null)
            {
                // get stub's request
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                String method = (String) inStream.readObject();
                // check method name
                if (method.equals("age"))
                {
                    // execute object server's business method
                    int age = person.getAge();
                    ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                    // return result to stub
                    outStream.writeInt(age);
                    outStream.flush();
                }
                if (method.equals("name"))
                {
                    // execute object server's business method
                    String name = person.getName();
                    ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                    // return result to stub
                    outStream.writeObject(name);
                    outStream.flush();
                }
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            System.exit(0);
        }
    }
}