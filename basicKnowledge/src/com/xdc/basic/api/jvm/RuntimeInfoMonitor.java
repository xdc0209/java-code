package com.xdc.basic.api.jvm;

public class RuntimeInfoMonitor
{
    public static void main(String[] args)
    {
        // Total number of processors or cores available to the JVM
        System.out.println("Total number of processors or cores available to the JVM (cores): "
                + Runtime.getRuntime().availableProcessors());

        // Total amount of free memory available to the JVM
        System.out.println(
                "Total amount of free memory available to the JVM (bytes): " + Runtime.getRuntime().freeMemory());

        // This will return Long.MAX_VALUE if there is no preset limit
        long maxMemory = Runtime.getRuntime().maxMemory();
        // Maximum amount of memory the JVM will attempt to use
        System.out.println("Maximum amount of memory the JVM will attempt to use (bytes): "
                + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        // Total memory currently in use by the JVM
        System.out.println(
                "Total memory currently in use by the JVM - heap size (bytes): " + Runtime.getRuntime().totalMemory());
    }
}
