package com.xdc.basic.api.rmi.sbus.server;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.rmi.sbus.message.SbusException;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeInfo;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeResult;
import com.xdc.basic.api.rmi.sbus.transport.MessageListener;

public class SbusServer implements MessageListener
{
    private static Logger log = LoggerFactory.getLogger(SbusServer.class);

    public byte[] receive(byte[] invokeInfoBytes)
    {
        // 反序列化调用信息
        SbusInvokeInfo invokeInfo = null;
        try
        {
            invokeInfo = (SbusInvokeInfo) SerializationUtils.deserialize(invokeInfoBytes);
        }
        catch (Throwable e)
        {
            log.error("Deserialize invoke info bytes failed.", e);
            SbusInvokeResult invokeResult = new SbusInvokeResult("deserialize_invoke_info_bytes_failed", e);
            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            return invokeResultBytes;
        }

        // 处理请求
        SbusInvokeResult invokeResult = null;
        try
        {
            SbusSkeleton<?> sbusSkeleton = SbusSkeletonManager.getSbusSkeleton(invokeInfo.getInvokeMethod().getClazz());
            if (sbusSkeleton == null)
            {
                SbusException sbusException = new SbusException(
                        String.format("Service [ %s ] not found.", invokeInfo.getInvokeMethod().getClazz()));
                invokeResult = new SbusInvokeResult(invokeInfo.getId(), sbusException);
                byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
                return invokeResultBytes;
            }

            invokeResult = sbusSkeleton.invoke(invokeInfo);
        }
        catch (Throwable e)
        {
            invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            return invokeResultBytes;
        }

        // 序列化调用结果
        try
        {
            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            return invokeResultBytes;
        }
        catch (Throwable e)
        {
            invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            return invokeResultBytes;
        }
    }
}
