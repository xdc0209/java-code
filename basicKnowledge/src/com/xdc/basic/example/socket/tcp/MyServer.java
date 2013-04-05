package com.xdc.basic.example.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MyServer
{
	public static void main(String[] args)
	{
		int count = 0;// 声明用来计数的int局部变量
		ServerSocket server = null;
		try
		{
			// 创建绑定到9876端口的ServerSocket对象
			server = new ServerSocket(9876);
			System.out.println("服务器对9876端口正在进行监听...");
			// 服务器循环接收客户端的请求，为不同的客户端提供服务
			while (true)
			{
				// 接收客户端的连接请求，若有连接请求返回连接对应的Socket对象
				Socket sc = server.accept();
				// 获取当前连接的输入流，并使用处理流进行封装
				DataInputStream din = new DataInputStream(sc.getInputStream());
				// 获取当前连接的输出流，并使用处理流进行封装
				DataOutputStream dout = new DataOutputStream(sc.getOutputStream());
				// 打印客户端的信息
				System.out.println("这是第" + (++count) + "个客户访问");
				System.out.println("客户端IP地址：" + sc.getInetAddress().getHostAddress());
				System.out.println("本地端口号：" + sc.getLocalPort());
				System.out.println("客户端信息：" + din.readUTF());
				// 向客户端发送回应信息
				dout.writeUTF("服务器的时间为：" + (new Date()));
				// 关闭流
				din.close();
				dout.close();
				// 关闭此Socket连接
				sc.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				server.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
