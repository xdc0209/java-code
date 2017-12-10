package com.xdc.basic.api.rmi.demo.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.xdc.basic.api.rmi.demo.intf.IHelloRMI;

/**
 * 接口的实现
 * 
 * @author xdc
 * 
 */
public class HelloRMIImpl extends UnicastRemoteObject implements IHelloRMI
{

    private static final long serialVersionUID = 1455115818555556704L;

    public HelloRMIImpl() throws RemoteException
    {
        super();
    }

    @Override
    public String sayHello() throws RemoteException
    {
        return "Hello RMI";
    }

}
