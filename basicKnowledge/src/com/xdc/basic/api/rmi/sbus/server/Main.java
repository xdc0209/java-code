package com.xdc.basic.api.rmi.sbus.server;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.xdc.basic.api.rmi.sbus.core.InvokeInfo;
import com.xdc.basic.api.rmi.sbus.core.InvokeResult;
import com.xdc.basic.skills.GetPath;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        String request = GetPath.connect(GetPath.getRelativePath(), "/../request.txt");
        String response = GetPath.connect(GetPath.getRelativePath(), "/../response.txt");

        byte[] invokeInfoBytes = FileUtils.readFileToByteArray(new File(request));
        InvokeInfo invokeInfo = (InvokeInfo) SerializationUtils.deserialize(invokeInfoBytes);

        Class<?> clazz = invokeInfo.getInvokeMethod().getClazz();
        Class<?> returnType = invokeInfo.getInvokeMethod().getReturnType();
        String name = invokeInfo.getInvokeMethod().getName();
        Class<?>[] parameterTypes = invokeInfo.getInvokeMethod().getParameterTypes();

        Method method = clazz.getMethod(name, parameterTypes);
        Object[] args2 = invokeInfo.getInvokeArgs();

        if (method == null)
        {
            throw new Exception("No such method.");
        }

        if (!returnType.isAssignableFrom(method.getReturnType()))
        {
            throw new Exception("Return type do not match.");
        }

        try
        {
            Object result = method.invoke(new HelloServiceImpl(), args2);
            InvokeResult invokeResult = new InvokeResult(invokeInfo.getId(), result);

            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            FileUtils.writeByteArrayToFile(new File(response), invokeResultBytes);
        }
        catch (Throwable e)
        {
            if (e instanceof InvocationTargetException)
            {
                e = ((InvocationTargetException) e).getTargetException();
            }
            InvokeResult invokeResult = new InvokeResult(invokeInfo.getId(), e);

            byte[] invokeResultBytes = SerializationUtils.serialize(invokeResult);
            FileUtils.writeByteArrayToFile(new File(response), invokeResultBytes);
        }
    }
}
