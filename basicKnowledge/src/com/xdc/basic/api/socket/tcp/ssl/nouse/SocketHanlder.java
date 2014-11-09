package com.xdc.basic.api.socket.tcp.ssl.nouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHanlder implements Runnable
{
    private Socket socket;

    public SocketHanlder(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            String data = reader.readLine();
            System.out.println("服务端接收消息：" + data);

            writer.println(data);
            writer.flush();
            System.out.println("服务端发送消息：" + data);

            reader.close();
            writer.close();

            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
