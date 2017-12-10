package com.xdc.basic.api.btrace;

import static com.sun.btrace.BTraceUtils.str;

import java.sql.Connection;
import java.util.Map;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnEvent;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

/**
 * 连接池泄露检测脚本，摘自网络，未验证
 * 
 * http://itindex.net/detail/50040-btrace-dbcp-%E6%95%B0%E6%8D%AE%E5%BA%93
 * http://www.blogjava.net/chen4765654/archive/2014/12/16/421472.html
 */
@BTrace(unsafe = true)
public class DataSourceTracer
{
    private static Map<Object, Object> map = BTraceUtils.newHashMap();

    @OnMethod(clazz = "org.apache.commons.dbcp.BasicDataSource", method = "getConnection", location = @Location(Kind.RETURN) )
    public static void traceExecute(@ProbeClassName String name, @ProbeMethodName String method,
            @Return Connection conn)
    {
        // BTraceUtils.println(strcat("获取连接:",BTraceUtils.str(conn)));
        Appendable buffer = BTraceUtils.Strings.newStringBuilder();
        BTraceUtils.Strings.append(buffer, BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss"));
        BTraceUtils.Strings.append(buffer, " - ");
        BTraceUtils.Strings.append(buffer, BTraceUtils.jstackStr());
        BTraceUtils.put(map, conn, str(buffer));
    }

    @OnMethod(clazz = "org.apache.commons.dbcp.PoolingDataSource$PoolGuardConnectionWrapper", method = "close")
    public static void traceExecute2(@ProbeClassName String name, @ProbeMethodName String method, @Self Connection conn)
    {
        // BTraceUtils.println(strcat("释放连接:",BTraceUtils.str(conn)));
        BTraceUtils.remove(map, conn);
    }

    @OnEvent
    public static void exit()
    {
        // 这里打印泄漏连接的方法堆栈，运行一段时间后通过Ctrl+C, 选择2, 发送事件打印
        BTraceUtils.printMap(map);
    }
}
