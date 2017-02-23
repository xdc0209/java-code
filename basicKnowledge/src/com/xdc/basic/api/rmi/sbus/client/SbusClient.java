package com.xdc.basic.api.rmi.sbus.client;

import com.xdc.basic.api.rmi.sbus.message.SbusInvokeInfo;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeResult;
import com.xdc.basic.api.rmi.sbus.transport.MessageClient;
import com.xdc.basic.api.rmi.sbus.util.SerializationUtil;

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
        byte[] invokeInfoBytes = SerializationUtil.serialize(invokeInfo);

        byte[] invokeResultBytes = messageClient.send(invokeInfoBytes);

        SbusInvokeResult invokeResult = (SbusInvokeResult) SerializationUtil.deserialize(invokeResultBytes);
        return invokeResult;
    }
}
