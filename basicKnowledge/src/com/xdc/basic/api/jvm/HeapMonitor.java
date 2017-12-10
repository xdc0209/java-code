package com.xdc.basic.api.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

import javax.management.MBeanServer;

import org.apache.commons.lang3.ArrayUtils;

public class HeapMonitor
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans)
        {
            System.out.println(garbageCollectorMXBean.getName());
            System.out.println(garbageCollectorMXBean.getCollectionCount());
            System.out.println(garbageCollectorMXBean.getCollectionTime());
            System.out.println(ArrayUtils.toString(garbageCollectorMXBean.getMemoryPoolNames()));
        }

        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeans)
        {
            System.out.println(ArrayUtils.toString(memoryManagerMXBean.getMemoryPoolNames()));
            System.out.println(memoryManagerMXBean.getName());
        }

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans)
        {
            System.out.println(memoryPoolMXBean.getName());
            System.out.println(memoryPoolMXBean.getType());
            System.out.println(memoryPoolMXBean.getUsage());
            System.out.println(memoryPoolMXBean.getCollectionUsage());
            System.out.println(memoryPoolMXBean.getPeakUsage());
            System.out.println(ArrayUtils.toString(memoryPoolMXBean.getMemoryManagerNames()));
        }

        System.out.println("uptime:" + ManagementFactory.getRuntimeMXBean().getUptime());
    }
}
