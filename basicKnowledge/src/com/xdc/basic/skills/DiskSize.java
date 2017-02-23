package com.xdc.basic.skills;

public class DiskSize
{
    public static void main(String[] args)
    {
        System.out.println("磁盘类中定义如下成员：private long size;");
        System.out.println("如果size表示磁盘容量，且单位为Byte，根据long的范围，我们的系统支持的磁盘大小为：");
        System.out.println("Byte " + Long.MAX_VALUE);
        System.out.println("KB   " + Long.MAX_VALUE / 1024);
        System.out.println("MB   " + Long.MAX_VALUE / 1024 / 1024);
        System.out.println("GB   " + Long.MAX_VALUE / 1024 / 1024 / 1024);
        System.out.println("TB   " + Long.MAX_VALUE / 1024 / 1024 / 1024 / 1024);
        System.out.println("PB   " + Long.MAX_VALUE / 1024 / 1024 / 1024 / 1024 / 1024);
        System.out.println("EB   " + Long.MAX_VALUE / 1024 / 1024 / 1024 / 1024 / 1024 / 1024);
    }
}
