package com.xdc.basic.api.socket.tcp.ssl.use;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.xdc.basic.skills.GetPath;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        // Set the trust store to use for validating the server cert.
        System.setProperty("javax.net.ssl.trustStore", GetPath.getRelativePath() + "keystore/truststore.jks");

        // 开启调试模式
        // System.setProperty("javax.net.debug", "ssl,handshake");

        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket socket = sf.createSocket("127.0.0.1", 8443);

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String dataSend = "hello";
        writer.println(dataSend);
        writer.flush();
        System.out.println("客户端发送消息: " + dataSend);

        String dataResive = reader.readLine();
        System.out.println("客户端接收消息: " + dataResive);

        writer.close();
        reader.close();

        socket.close();
    }
}
