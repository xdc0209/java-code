package com.xdc.basic.api.socket.tcp.demo2;

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

            System.out.println("读取字符开始。");
            char[] buffer = new char[8192];
            int bufferUsed = 0;
            while ((bufferUsed = reader.read(buffer, 0, buffer.length)) > 0) // 注意：1.无数据时read方法是阻塞的。2.客户端调用outputStream.close()时，不阻塞，返回-1。
            {
                System.out.println("读到[" + bufferUsed + "]个字符。");
                System.out.println("处理数据开始。");
                System.out.println(String.valueOf(buffer, 0, bufferUsed));
                System.out.println("处理数据结束。");
            }
            System.out.println("读取字符结束。读取函数返回值：" + bufferUsed);

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
