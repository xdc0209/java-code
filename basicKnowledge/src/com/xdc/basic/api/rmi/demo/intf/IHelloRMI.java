package com.xdc.basic.api.rmi.demo.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 远程接口
 * 
 * @author xdc
 * 
 */
public interface IHelloRMI extends Remote
{
    public String sayHello() throws RemoteException;
}
