package com.xdc.basic.example.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MyClient
{
	public static void main(String[] args)
	{
		try
		{
			// 创建连接到服务器的Socket对象
			Socket sc = new Socket("127.0.0.1", 9876);
			// 获取当前连接的输入流，并使用处理流进行封装
			DataInputStream din = new DataInputStream(sc.getInputStream());
			// 获取当前连接的输出流，并使用处理流进行封装
			DataOutputStream dout = new DataOutputStream(sc.getOutputStream());
			// 向服务器发送消息
			dout.writeUTF("请问你那现在几点？");
			// 读取服务器的返回消息并打印
			System.out.println(din.readUTF());
			// 关闭流
			din.close();
			dout.close();
			// 关闭此Socket连接
			sc.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
