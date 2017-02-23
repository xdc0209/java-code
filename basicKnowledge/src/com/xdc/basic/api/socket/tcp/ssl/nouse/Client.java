package com.xdc.basic.api.socket.tcp.ssl.nouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 8080);

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String dataSend = "hello";
        writer.println(dataSend);
        writer.flush();
        System.out.println("客户端发送消息： " + dataSend);

        String dataResive = reader.readLine();
        System.out.println("客户端接收消息：" + dataResive);

        writer.close();
        reader.close();

        socket.close();
    }
}
