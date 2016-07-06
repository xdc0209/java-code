package com.xdc.basic.api.rmi.sbus.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;

public class InvokeInfo implements Serializable
{
    private static final long serialVersionUID = 1712593749778467036L;

    // 消息id，用于消息跟踪。在调用信息（InvokeInfo）中取出，然后设置到调用结果（InvokeResult）中。
    private String            id               = UUID.randomUUID().toString();

    private InvokeMethod      invokeMethod;

    private Object[]          invokeArgs;

    public InvokeInfo()
    {
    }

    public InvokeInfo(Method method, Object[] args)
    {
        this.invokeMethod = new InvokeMethod(method.getDeclaringClass(), method.getReturnType(), method.getName(),
                method.getParameterTypes());
        this.invokeArgs = args;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public InvokeMethod getInvokeMethod()
    {
        return invokeMethod;
    }

    public void setInvokeMethod(InvokeMethod invokeMethod)
    {
        this.invokeMethod = invokeMethod;
    }

    public Object[] getInvokeArgs()
    {
        return invokeArgs;
    }

    public void setInvokeArgs(Object[] invokeArgs)
    {
        this.invokeArgs = invokeArgs;
    }

    public class InvokeMethod implements Serializable
    {
        private static final long serialVersionUID = 4391215544442431815L;

        private Class<?>          clazz;
        private Class<?>          returnType;
        private String            name;
        private Class<?>[]        parameterTypes;

        public InvokeMethod()
        {
        }

        public InvokeMethod(Class<?> clazz, Class<?> returnType, String name, Class<?>... parameterTypes)
        {
            this.clazz = clazz;
            this.returnType = returnType;
            this.name = name;
            this.parameterTypes = parameterTypes;
        }

        public Class<?> getClazz()
        {
            return clazz;
        }

        public void setClazz(Class<?> clazz)
        {
            this.clazz = clazz;
        }

        public Class<?> getReturnType()
        {
            return returnType;
        }

        public void setReturnType(Class<?> returnType)
        {
            this.returnType = returnType;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Class<?>[] getParameterTypes()
        {
            return parameterTypes;
        }

        public void setParameterTypes(Class<?>[] parameterTypes)
        {
            this.parameterTypes = parameterTypes;
        }
    }
}