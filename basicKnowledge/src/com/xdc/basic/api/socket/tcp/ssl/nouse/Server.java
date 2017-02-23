package com.xdc.basic.api.socket.tcp.ssl.nouse;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器对8080端口正在进行监听...");

        ExecutorService exec = Executors.newFixedThreadPool(10);
        while (true)
        {
            Socket sc = serverSocket.accept();
            System.out.println("接受并处理scoket连接： " + sc.getInetAddress().getHostAddress() + ":" + sc.getPort());

            SocketHanlder scocketHanlder = new SocketHanlder(sc);
            exec.submit(scocketHanlder);
        }
    }
}
