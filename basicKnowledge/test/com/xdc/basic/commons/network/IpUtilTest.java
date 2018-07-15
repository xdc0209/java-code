package com.xdc.basic.commons.network;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class IpUtilTest
{
    @Test
    public void testIsIpv4Ip()
    {
        Assert.assertEquals(true, IpUtil.isIpv4Ip("255.255.255.255"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("254.1.1.1"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("244.1.1.1"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("144.1.1.1"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("44.1.1.1"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("4.1.1.1"));
        Assert.assertEquals(true, IpUtil.isIpv4Ip("0.0.0.0"));

        Assert.assertEquals(false, IpUtil.isIpv4Ip("0.0.0.0 "));
        Assert.assertEquals(false, IpUtil.isIpv4Ip("0.0.0.0hehe"));
    }

    @Test
    public void testIsIpv4Mask()
    {
        Assert.assertEquals(true, IpUtil.isIpv4Mask("255.0.0.0"));
        Assert.assertEquals(true, IpUtil.isIpv4Mask("252.0.0.0"));
        Assert.assertEquals(true, IpUtil.isIpv4Mask("128.0.0.0"));
        Assert.assertEquals(true, IpUtil.isIpv4Mask("0.0.0.0"));

        Assert.assertEquals(false, IpUtil.isIpv4Mask("128.0.0.1"));
        Assert.assertEquals(false, IpUtil.isIpv4Ip("0.0.0.0 "));
        Assert.assertEquals(false, IpUtil.isIpv4Ip("0.0.0.0hehe"));
    }

    @Ignore("比较耗时。")
    @Test
    public void testIsIpv4PortAndIsIpv4PortRegex()
    {
        // 测试Java校验和正则表达式校验的效率
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            Assert.assertEquals(false, IpUtil.isIpv4Port("-1"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("0"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("1"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("999"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("1024"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("1025"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("9999"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("65534"));
            Assert.assertEquals(true, IpUtil.isIpv4Port("65535"));
            Assert.assertEquals(false, IpUtil.isIpv4Port("65536"));
            Assert.assertEquals(false, IpUtil.isIpv4Port("99999"));
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            Assert.assertEquals(false, IpUtil.isIpv4PortRegex("-1"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("0"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("1"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("999"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("1024"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("1025"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("9999"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("65534"));
            Assert.assertEquals(true, IpUtil.isIpv4PortRegex("65535"));
            Assert.assertEquals(false, IpUtil.isIpv4PortRegex("65536"));
            Assert.assertEquals(false, IpUtil.isIpv4PortRegex("99999"));
        }
        long end2 = System.currentTimeMillis();

        System.out.println("Java : " + (end1 - start1)); // Java : 78
        System.out.println("Regex: " + (end2 - start2)); // Regex: 6072
    }

    @Test
    public void testIsInSameSubnet()
    {
        Assert.assertEquals(true, IpUtil.isInSameSubnet("192.168.1.101", "192.168.1.102", "255.255.255.0"));

        Assert.assertEquals(false, IpUtil.isInSameSubnet("192.168.1.101", "192.168.2.102", "255.255.255.0"));
    }
}
