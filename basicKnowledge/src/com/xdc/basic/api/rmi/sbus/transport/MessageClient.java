package com.xdc.basic.api.rmi.sbus.transport;

public interface MessageClient
{
    public byte[] send(byte[] request) throws Exception;
}
