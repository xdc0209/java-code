package com.xdc.basic.api.rmi.sbus.transport;

public interface MessageListener
{
    public byte[] receive(byte[] request) throws Exception;
}
