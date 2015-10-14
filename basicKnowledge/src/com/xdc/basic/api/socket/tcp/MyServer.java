package com.xdc.basic.api.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.io.IOUtils;

public class MyServer
{
    public static void main(String[] args)
    {
        int count = 0; // 声明用来计数的int局部变量
        ServerSocket serverSocket = null;
        try
        {
            // 创建绑定到9876端口的ServerSocket对象
            serverSocket = new ServerSocket(9876);
            System.out.println("服务器对9876端口正在进行监听...");

            // 服务器循环接收客户端的请求，为不同的客户端提供服务
            while (true)
            {
                // 接收客户端的连接请求，若有连接请求返回连接对应的Socket对象
                Socket socket = serverSocket.accept();

                // 获得Socket后，应开启线程处理对应的socket连接，而主线程继续保持监听，这是个简单例子就不扩展了。

                // 获取当前连接的输入流，并使用处理流进行封装
                DataInputStream din = new DataInputStream(socket.getInputStream());
                // 获取当前连接的输出流，并使用处理流进行封装
                DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

                // 打印客户端的信息
                System.out.println("这是第" + (++count) + "个客户访问");
                System.out.println("服务端IP地址：" + socket.getLocalAddress().getHostAddress());
                System.out.println("服务端端口号：" + socket.getLocalPort());
                System.out.println("客户端IP地址：" + socket.getInetAddress().getHostAddress());
                System.out.println("客户端端口号：" + socket.getPort());

                System.out.println("客户端信息：" + din.readUTF());
                // 向客户端发送回应信息
                dout.writeUTF("服务器的时间为：" + (new Date()));

                // 关闭流
                din.close();
                dout.close();

                // 关闭此Socket连接
                socket.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(serverSocket);
        }
    }
}
