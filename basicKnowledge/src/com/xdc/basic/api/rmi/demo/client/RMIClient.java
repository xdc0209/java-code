package com.xdc.basic.api.rmi.demo.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.xdc.basic.api.rmi.demo.intf.IHelloRMI;

/**
 * 客户端程序
 * 
 * @author xdc
 * 
 */
public class RMIClient
{
    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        try
        {
            String ip = new String();
            int port;
            System.out.print("请输入服务器ip：");
            ip = cin.nextLine();
            System.out.print("请输入服务器端口号：");
            port = cin.nextInt();

            IHelloRMI iRmi = (IHelloRMI) Naming.lookup(String.format("rmi://%s:%s/My_RMI", ip, port));

            System.out.println(iRmi.sayHello());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            cin.close();
        }
    }
}
