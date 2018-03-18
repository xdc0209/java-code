package com.xdc.basic.api.socket.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.commons.PathUtil;

class FortuneServer
{
    public static void main(String args[])
    {
        String curPath = PathUtil.getRelativePath();

        DatagramSocket serverSocket = null;
        FileInputStream inStream = null;
        try
        {
            // 创建绑定到1114端口的ServerSocket对象
            serverSocket = new DatagramSocket(1114);

            while (true)
            {
                // 创建缓冲区
                byte[] data = new byte[256];
                // 创建接收数据包
                DatagramPacket rPacket = new DatagramPacket(data, data.length);

                System.out.println("等待客户端连接...");
                // 等待接收数据包
                serverSocket.receive(rPacket);
                System.out.println("已有客户端发来请求: " + rPacket.getAddress().getHostAddress() + ":" + rPacket.getPort());

                // 读取待发送的内容
                inStream = new FileInputStream(new File(curPath + "Fortunes.txt"));
                if (inStream.read(data) <= 0)
                {
                    System.err.println("Error: couldn't read fortunes");
                }

                // 创建发送数据包
                DatagramPacket sPacket = new DatagramPacket(data, data.length, rPacket.getAddress(), rPacket.getPort());
                // 发送报文
                serverSocket.send(sPacket);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(serverSocket);
            IOUtils.closeQuietly(inStream);
        }
    }
}
