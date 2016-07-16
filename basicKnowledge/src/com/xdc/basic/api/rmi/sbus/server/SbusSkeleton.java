package com.xdc.basic.api.rmi.sbus.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xdc.basic.api.rmi.sbus.message.SbusException;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeInfo;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeResult;

public class SbusSkeleton<T>
{
    /**
     * 服务实例
     */
    private T serviceInstance = null;;

    public SbusSkeleton(T serviceInstance)
    {
        this.serviceInstance = serviceInstance;
    }

    public SbusInvokeResult invoke(SbusInvokeInfo invokeInfo)
    {
        Class<?> clazz = invokeInfo.getInvokeMethod().getClazz();
        Class<?> returnType = invokeInfo.getInvokeMethod().getReturnType();
        String name = invokeInfo.getInvokeMethod().getName();
        Class<?>[] parameterTypes = invokeInfo.getInvokeMethod().getParameterTypes();
        Object[] args = invokeInfo.getInvokeArgs();

        Method method = null;
        try
        {
            method = clazz.getMethod(name, parameterTypes);

        }
        catch (NoSuchMethodException e)
        {
            SbusInvokeResult invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            return invokeResult;
        }
        catch (SecurityException e)
        {
            SbusInvokeResult invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            return invokeResult;
        }

        // A.class.isAssignableFrom(B.class) 判断类B是否是类A本身，或者是其子类。
        // List.class.isAssignableFrom(ArrayList.class); 此据返回值为true。
        if (!returnType.isAssignableFrom(method.getReturnType()))
        {
            SbusException e = new SbusException(String.format(
                    "Return type does not math. Client method [ %s %s.%s%s ] and server metchod [ %s %s.%s%s ].",
                    returnType.getName(), clazz.getName(), name, argumentTypesToString(parameterTypes),
                    method.getReturnType().getName(), clazz.getName(), name, argumentTypesToString(parameterTypes)));
            SbusInvokeResult invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            return invokeResult;
        }

        try
        {
            Object result = method.invoke(serviceInstance, args);
            SbusInvokeResult invokeResult = new SbusInvokeResult(invokeInfo.getId(), result);
            return invokeResult;
        }
        catch (Throwable e)
        {
            if (e instanceof InvocationTargetException)
            {
                e = ((InvocationTargetException) e).getTargetException();
            }
            SbusInvokeResult invokeResult = new SbusInvokeResult(invokeInfo.getId(), e);
            return invokeResult;
        }
    }

    /**
     * 取自： private String java.lang.Class.argumentTypesToString(Class<?>[] argTypes)
     */
    private String argumentTypesToString(Class<?>[] argTypes)
    {
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        if (argTypes != null)
        {
            for (int i = 0; i < argTypes.length; i++)
            {
                if (i > 0)
                {
                    buf.append(", ");
                }
                Class<?> c = argTypes[i];
                buf.append((c == null) ? "null" : c.getName());
            }
        }
        buf.append(")");
        return buf.toString();
    }
}
