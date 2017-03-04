package com.xdc.basic.commons.network;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class IpUtil
{
    // IP 示例：127.0.0.1
    private static final String ipv4IpRegex                = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

    // Mask 示例：255.255.0.0
    private static final String ipv4MaskRegex              = "^((254|252|248|240|224|192|128|0)\\.0\\.0\\.0)$|^(255\\.(254|252|248|240|224|192|128|0)\\.0\\.0)$|^(255\\.255\\.(254|252|248|240|224|192|128|0)\\.0)$|^(255\\.255\\.255\\.(254|252|248|240|224|192|128|0))$";

    // Port 范围：1-65535
    private static final String ipv4PortRegex              = "^[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    // DynamicPort 范围：1024-65535
    private static final String ipv4DynamicPortRegex       = "^102[4-9]|10[3-9][0-9]|1[1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    // IP:Port 示例：127.0.0.1:1023
    private static final String ipv4IpPortRegex            = String.format("^(%s):(%s)$",
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"), StringUtils.substringBetween(ipv4PortRegex, "^", "$"));

    // IP:DynamicPort 示例：127.0.0.1:1024
    private static final String ipv4IpDynamicPortRegex     = String.format("^(%s):(%s)$",
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"),
            StringUtils.substringBetween(ipv4DynamicPortRegex, "^", "$"));;

    // IP1,IP2,IP3,... 示例：127.0.0.1,127.0.0.2,127.0.0.3
    private static final String ipv4IpListRegex            = String.format("^(%s)(,(%s))*$",
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"), StringUtils.substringBetween(ipv4IpRegex, "^", "$"));

    // IP1:Port1,IP2:Port2,IP3:Port3,... 示例：127.0.0.1:1023,127.0.0.2:1024,127.0.0.3:65535
    private static final String ipv4IpPortListRegex        = String.format("^((%s):(%s))(,((%s):(%s)))*$",
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"), StringUtils.substringBetween(ipv4PortRegex, "^", "$"),
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"), StringUtils.substringBetween(ipv4PortRegex, "^", "$"));

    // IP1:DynamicPort1,IP2:DynamicPort2,IP3:DynamicPort3,... 示例：127.0.0.1:1024,127.0.0.2:1025,127.0.0.3:65535
    private static final String ipv4IpDynamicPortListRegex = String.format("^((%s):(%s))(,((%s):(%s)))*$",
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"),
            StringUtils.substringBetween(ipv4DynamicPortRegex, "^", "$"),
            StringUtils.substringBetween(ipv4IpRegex, "^", "$"),
            StringUtils.substringBetween(ipv4DynamicPortRegex, "^", "$"));

    public static boolean isIpv4Ip(String ip)
    {
        if (StringUtils.isBlank(ip))
        {
            return false;
        }

        return ip.matches(ipv4IpRegex);
    }

    public static boolean isIpv4Mask(String mask)
    {
        if (StringUtils.isBlank(mask))
        {
            return false;
        }

        return mask.matches(ipv4MaskRegex);
    }

    public static boolean isIpv4Port(String port)
    {
        if (StringUtils.isBlank(port))
        {
            return true;
        }

        int portNumber = NumberUtils.toInt(port, -1);
        if (portNumber >= 0 && portNumber <= 65535)
        {
            return true;
        }

        return false;
    }

    public static boolean isIpv4PortRegex(String port)
    {
        if (StringUtils.isBlank(port))
        {
            return true;
        }

        return port.matches(ipv4PortRegex);
    }

    public static boolean isIpv4DynamicPort(String dynamicPort)
    {
        if (StringUtils.isBlank(dynamicPort))
        {
            return true;
        }

        int portNumber = NumberUtils.toInt(dynamicPort, -1);
        if (portNumber >= 1024 && portNumber <= 65535)
        {
            return true;
        }

        return false;
    }

    public static boolean isIpv4DynamicPortRegex(String dynamicPort)
    {
        if (StringUtils.isBlank(dynamicPort))
        {
            return true;
        }

        return dynamicPort.matches(ipv4DynamicPortRegex);
    }

    public static boolean isIpv4IpPort(String ipPort)
    {
        if (StringUtils.isBlank(ipPort))
        {
            return false;
        }

        return ipPort.matches(ipv4IpPortRegex);
    }

    public static boolean isIpv4IpDynamicPort(String ipDynamicPort)
    {
        if (StringUtils.isBlank(ipDynamicPort))
        {
            return false;
        }

        return ipDynamicPort.matches(ipv4IpDynamicPortRegex);
    }

    public static boolean isIpv4IpList(String ipList)
    {
        if (StringUtils.isBlank(ipList))
        {
            return false;
        }

        return ipList.matches(ipv4IpListRegex);
    }

    public static boolean isIpv4IpPortList(String ipPortList)
    {
        if (StringUtils.isBlank(ipPortList))
        {
            return false;
        }

        return ipPortList.matches(ipv4IpPortListRegex);
    }

    public static boolean isIpv4IpDynamicPortList(String ipDynamicPortList)
    {
        if (StringUtils.isBlank(ipDynamicPortList))
        {
            return false;
        }

        return ipDynamicPortList.matches(ipv4IpDynamicPortListRegex);
    }

    public static boolean isInSameSubnet(String ip1, String ip2, String mask)
    {
        if (!isIpv4Ip(ip1) || !isIpv4Ip(ip2) || !isIpv4Mask(mask))
        {
            return false;
        }

        int ip1Int = ipToInt(ip1);
        int ip2Int = ipToInt(ip2);

        int maskInt = ipToInt(mask);

        // System.out.println(Integer.toBinaryString(ip1Int));
        // System.out.println(Integer.toBinaryString(ip2Int));
        // System.out.println(Integer.toBinaryString(maskInt));
        //
        // System.out.println(Integer.toBinaryString(ip1Int & maskInt));
        // System.out.println(Integer.toBinaryString(ip2Int & maskInt));

        return (ip1Int & maskInt) == (ip2Int & maskInt);
    }

    private static int ipToInt(String ipString)
    {
        if (!isIpv4Ip(ipString))
        {
            throw new IllegalArgumentException("Ip [" + ipString + "] is not valid.");
        }

        String[] ips = ipString.split("\\.");

        int part1 = Integer.parseInt(ips[0]) << 24;
        int part2 = Integer.parseInt(ips[1]) << 16;
        int part3 = Integer.parseInt(ips[2]) << 8;
        int part4 = Integer.parseInt(ips[3]);

        int ipInt = part1 | part2 | part3 | part4;

        return ipInt;
    }

    public static void main(String[] args)
    {
        System.out.println(ipv4IpRegex);
        System.out.println(ipv4MaskRegex);
        System.out.println(ipv4PortRegex);
        System.out.println(ipv4DynamicPortRegex);
        System.out.println(ipv4IpListRegex);
        System.out.println(ipv4IpDynamicPortListRegex);

        String ip1 = "254.1.1.1";
        String ip2 = "244.1.1.1";
        String ip3 = "144.1.1.1";
        String ip4 = "44.1.1.1";
        String ip5 = "4.1.1.1";
        System.out.println(isIpv4Ip(ip1));
        System.out.println(isIpv4Ip(ip2));
        System.out.println(isIpv4Ip(ip3));
        System.out.println(isIpv4Ip(ip4));
        System.out.println(isIpv4Ip(ip5));

        String mask1 = "255.0.0.0";
        String mask2 = "252.0.0.0";
        String mask3 = "128.0.0.0";
        System.out.println(isIpv4Mask(mask1));
        System.out.println(isIpv4Mask(mask2));
        System.out.println(isIpv4Mask(mask3));

        System.out.println(isInSameSubnet("128.128.5.2", "128.128.5.2", "255.255.0.0"));

        // 测试正则表达式和java校验的效率
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            isIpv4DynamicPortRegex("-1");
            isIpv4DynamicPortRegex("0");
            isIpv4DynamicPortRegex("1");
            isIpv4DynamicPortRegex("999");
            isIpv4DynamicPortRegex("1023");
            isIpv4DynamicPortRegex("1024");
            isIpv4DynamicPortRegex("1025");
            isIpv4DynamicPortRegex("9999");
            isIpv4DynamicPortRegex("65534");
            isIpv4DynamicPortRegex("65535");
            isIpv4DynamicPortRegex("65536");
            isIpv4DynamicPortRegex("99999");
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            isIpv4DynamicPort("-1");
            isIpv4DynamicPort("0");
            isIpv4DynamicPort("1");
            isIpv4DynamicPort("999");
            isIpv4DynamicPort("1023");
            isIpv4DynamicPort("1024");
            isIpv4DynamicPort("1025");
            isIpv4DynamicPort("9999");
            isIpv4DynamicPort("65534");
            isIpv4DynamicPort("65535");
            isIpv4DynamicPort("65536");
            isIpv4DynamicPort("99999");
        }
        long end2 = System.currentTimeMillis();

        System.out.println("Regexp: " + (end1 - start1)); // Regexp: 6072
        System.out.println("Java  : " + (end2 - start2)); // Java : 78
    }
}