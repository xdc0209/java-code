package com.xdc.basic.example.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

class FortuneClient
{
	public String getMessage()
	{
		String fortune;
		try
		{
			// 创建数据报套接字
			DatagramSocket socket = new DatagramSocket();

			// 创建缓冲区
			byte[] data = new byte[256];
			// 创建发送数据包
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), 1114);
			// 发送报文
			socket.send(sendPacket);

			// 创建接收数据包
			DatagramPacket resivePacket = new DatagramPacket(data, data.length);
			// 等待接收报文
			socket.receive(resivePacket);

			// 将数据内容由字节转换成字符
			fortune = new String(resivePacket.getData());
			// 关闭套接字
			socket.close();
		}
		catch (UnknownHostException e)
		{
			System.err.println("Exception: host could not be found!");
			return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return fortune;
	}

	public static void main(String args[])
	{
		FortuneClient client = new FortuneClient();
		System.out.println(client.getMessage());
	}
}
