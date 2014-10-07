package com.xdc.basic.api.jmx.con2jvmmgr;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Con2
{
    // 2.监控应用与被监控应用不位于同一JVM
    //   1)首先在被监控的JVM的启动参数中加入如下的启动参数以启JVM代理
    //     -Dcom.sun.management.jmxremote 
    //     -Dcom.sun.management.jmxremote.port=127.0.0.1:8000
    //     -Dcom.sun.management.jmxremote.authenticate=false
    //     -Dcom.sun.management.jmxremote.ssl=false
    //   2)连接到代理上
    public static void main(String[] args) throws IOException
    {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:8000/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(mbsc, "java.lang:type=Runtime",
                RuntimeMXBean.class);

        System.out.println(rmxb.getName());
    }
}
