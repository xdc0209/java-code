package com.xdc.basic.api.rmi.sbus.client;

import org.apache.commons.lang3.SerializationUtils;

import com.xdc.basic.api.rmi.sbus.message.SbusInvokeInfo;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeResult;
import com.xdc.basic.api.rmi.sbus.transport.MessageClient;

public class SbusClient
{
    private MessageClient messageClient;

    public SbusClient(MessageClient messageClient)
    {
        super();
        this.messageClient = messageClient;
    }

    public SbusInvokeResult invoke(SbusInvokeInfo invokeInfo) throws Exception
    {
        byte[] invokeInfoBytes = SerializationUtils.serialize(invokeInfo);

        byte[] invokeResultBytes = messageClient.send(invokeInfoBytes);

        SbusInvokeResult invokeResult = (SbusInvokeResult) SerializationUtils.deserialize(invokeResultBytes);
        return invokeResult;
    }
}
