package com.xdc.basic.api.jmx.con2jvmmgr;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.management.MBeanServer;

public class Con1
{
    // 1.监控应用与被监控应用位于同一JVM
    public static void main(String[] args) throws IOException
    {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(server, "java.lang:type=Runtime",
                RuntimeMXBean.class);

        System.out.println(rmxb.getName());
    }
}
