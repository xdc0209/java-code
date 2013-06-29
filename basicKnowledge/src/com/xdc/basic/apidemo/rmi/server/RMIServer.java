package com.xdc.basic.apidemo.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * 服务器端
 * 
 * RMI，远程方法调用（Remote Method Invocation）是Enterprise JavaBeans的支柱，
 * 是建立分布式Java应用程序的方便途径。RMI是非常容易使用的，但是它非常的强大。
 * 
 * RMI的基础是接口，RMI构架基于一个重要的原理：定义接口和定义接口的具体实现是分开的。
 * 
 * @author xdc
 * 
 */
public class RMIServer
{
	public static void main(String[] args)
	{
		Scanner cin = new Scanner(System.in);
		try
		{
			int port;
			System.out.print("请输入监听端口号：");
			port = cin.nextInt();
			HelloRMIImpl rmi = new HelloRMIImpl();
			Registry r = LocateRegistry.createRegistry(port);
			r.rebind("My_RMI", rmi);
			System.out.println("服务运行中...");
			System.out.println("服务端口号为：" + port);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		finally
		{
			cin.close();
		}
	}
}
