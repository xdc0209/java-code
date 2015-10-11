package com.xdc.basic.api.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.TLS;

/**
 * Btrace简单样例
 */
@BTrace(unsafe = true)
public class HelloWorldTracer
{
    // 注意：
    // 这个方法输出在Btrace上：  BTraceUtils.println("Log in Btrace start-------------------------------------------");
    // 这个方法输出在java程序上：System.out.println("Log in target java start-------------------------------------------");

    @TLS
    private static long startTime = 0;

    @OnMethod(clazz = "com.xdc.basic.api.btrace.HelloWorld", method = "execute")
    public static void startMethod()
    {
        BTraceUtils.println("Log in Btrace start-------------------------------------------");
        System.out.println("Log in target java start-------------------------------------------");

        startTime = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = "com.xdc.basic.api.btrace.HelloWorld", method = "execute", location = @Location(Kind.RETURN))
    public static void endMethod()
    {
        BTraceUtils.println(BTraceUtils.strcat("the class method execute time=>",
                BTraceUtils.str(BTraceUtils.timeMillis() - startTime)));

        System.out.println("Log in target java end-------------------------------------------");
        BTraceUtils.println("Log in Btrace end-------------------------------------------");
        BTraceUtils.println();
    }

    @OnMethod(clazz = "com.xdc.basic.api.btrace.HelloWorld", method = "execute", location = @Location(Kind.RETURN))
    public static void traceExecute(@ProbeClassName String name, @ProbeMethodName String method, int sleepTime)
    {
        BTraceUtils.println(BTraceUtils.strcat("the class name=>", name));
        BTraceUtils.println(BTraceUtils.strcat("the class method=>", method));
        BTraceUtils.println(BTraceUtils.strcat("the class method params=>", BTraceUtils.str(sleepTime)));
    }
}
