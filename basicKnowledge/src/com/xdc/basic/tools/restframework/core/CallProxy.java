package com.xdc.basic.tools.restframework.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.http.HttpStatus;

import com.xdc.basic.tools.restclient.constants.HttpMethod;
import com.xdc.basic.tools.restclient.message.Request;
import com.xdc.basic.tools.restclient.message.Response;
import com.xdc.basic.tools.restclient.tools.JsonTool;
import com.xdc.basic.tools.restframework.message.CallerException;
import com.xdc.basic.tools.restframework.util.CallerTool;

public class CallProxy implements InvocationHandler
{
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        // 存放结果
        Object result = null;

        RestMethod restMethod = method.getAnnotation(RestMethod.class);
        MethodType methodType = restMethod.method();
        String url = restMethod.url();

        Request req = new Request();

        if (methodType == MethodType.GET)
        {
            Object[] urlVariables = CallerTool.resolveUrlVariables(MethodType.GET, args);

            req.setMethod(HttpMethod.GET.toString());
            req.setUrl(CallerTool.expandUrl(url, urlVariables));
        }
        else if (methodType == MethodType.POST)
        {
            Object[] urlVariables = CallerTool.resolveUrlVariables(MethodType.POST, args);
            Object body = CallerTool.resolveBody(MethodType.POST, args);

            req.setMethod(HttpMethod.POST.toString());
            req.setUrl(CallerTool.expandUrl(url, urlVariables));
            req.setBody(JsonTool.toJSONString(body));
        }
        else if (methodType == MethodType.PUT)
        {
            Object[] urlVariables = CallerTool.resolveUrlVariables(MethodType.PUT, args);
            Object body = CallerTool.resolveBody(MethodType.PUT, args);

            req.setMethod(HttpMethod.PUT.toString());
            req.setUrl(CallerTool.expandUrl(url, urlVariables));
            req.setBody(JsonTool.toJSONString(body));
        }
        else if (methodType == MethodType.DELETE)
        {
            Object[] urlVariables = CallerTool.resolveUrlVariables(MethodType.DELETE, args);

            req.setMethod(HttpMethod.DELETE.toString());
            req.setUrl(CallerTool.expandUrl(url, urlVariables));
        }
        else
        {
            throw new UnsupportedOperationException("Method " + methodType + " is not support.");
        }

        System.out.println("Send rest requst: " + req);
        Response rsp = CallerFrameWork.getRestClient().handldeRequset(req);
        System.out.println("Receive rest response: " + rsp);

        if (rsp.getStatusCode() != HttpStatus.SC_OK)
        {
            // 这个需要根据具体的业务，再进行错误解析
            throw new CallerException("Invoke failed: " + rsp);
        }

        if (needResolveRespose(method))
        {
            result = JsonTool.parse(rsp.getBody(), method.getReturnType());
        }

        return result;
    }

    private boolean needResolveRespose(Method method)
    {
        return !void.class.isAssignableFrom(method.getReturnType())
                && !Void.class.isAssignableFrom(method.getReturnType());
    }

    public static Object factory(Class<?> clazz)
    {
        ClassLoader classLoader = clazz.getClassLoader();

        Class<?>[] interfaces;
        if (clazz.isInterface())
        {
            interfaces = new Class<?>[] { clazz };
        }
        else
        {
            interfaces = clazz.getInterfaces();
        }

        // 取得代理对象, 要绑定接口(这是jdk动态代理的一个缺陷，cglib动态代理弥补了这一缺陷)
        return Proxy.newProxyInstance(classLoader, interfaces, new CallProxy());
    }
}
