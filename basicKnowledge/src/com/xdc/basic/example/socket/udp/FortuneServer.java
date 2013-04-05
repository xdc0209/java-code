package com.xdc.basic.example.socket.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;

class FortuneServer extends Thread
{
	DatagramSocket	ServerSocket;

	public FortuneServer()
	{
		super("FortuneServer");
		try
		{
			ServerSocket = new DatagramSocket(1114);
			System.out.println("FortuneServer up and running...");
		}
		catch (SocketException e)
		{
			System.err.println("Exception: couldn't create datagram sockter");
			System.exit(1);
		}
	}

	public static void main(String args[])
	{
		FortuneServer server = new FortuneServer();
		server.start();
	}

	public void run()
	{
		// 获取当前路径
		String curClassName = new Throwable().getStackTrace()[0].getClassName();
		String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
		String curPath = "src\\" + curPackage.replace(".", "\\") + "\\";

		FileInputStream inStream = null;
		while (true)
		{
			try
			{
				// 创建缓冲区
				byte[] data = new byte[256];
				// 创建接收数据包
				DatagramPacket rPacket = new DatagramPacket(data, data.length);
				// 等待接收数据包
				ServerSocket.receive(rPacket);

				// 读取待发送的内容
				inStream = new FileInputStream(new File(curPath + "Fortunes.txt"));
				if (inStream.read(data) <= 0)
				{
					System.err.println("Error: couldn't read fortunes");
				}

				// 创建发送数据包
				DatagramPacket sPacket = new DatagramPacket(data, data.length, rPacket.getAddress(), rPacket.getPort());
				// 发送报文
				ServerSocket.send(sPacket);
			}
			catch (Exception e)
			{
				System.err.println("Exception: " + e);
				e.printStackTrace();
			}
			finally
			{
				// 大师鸟悄的关闭输入流
				IOUtils.closeQuietly(inStream);
			}
		}
	}

}
