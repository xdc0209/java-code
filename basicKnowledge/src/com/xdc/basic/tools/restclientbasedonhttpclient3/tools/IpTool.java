package com.xdc.basic.tools.restclientbasedonhttpclient3.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class IpTool
{
    // IP >>>> 127.0.0.1
    private static final String ipv4IpRegExp                = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

    // Mask >>>> 255.255.0.0
    private static final String ipv4MaskRegExp              = "^((254|252|248|240|224|192|128|0)\\.0\\.0\\.0)$|^(255\\.(254|252|248|240|224|192|128|0)\\.0\\.0)$|^(255\\.255\\.(254|252|248|240|224|192|128|0)\\.0)$|^(255\\.255\\.255\\.(254|252|248|240|224|192|128|0))$";

    // Port >>>> 1-65535
    private static final String ipv4PortRegExp              = "^[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    // DynamicPort >>>> 1024-65535
    private static final String ipv4DynamicPortRegExp       = "^102[4-9]|10[3-9][0-9]|1[1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    // Mac >>>> 00:0C:29:C2:A9:A0
    private static final String macRegExp                   = "^([a-fA-F0-9]{2}[-:]){5}([a-fA-F0-9]){2}$";

    // IP1,IP2,IP3 >>>> 127.0.0.1,127.0.0.2,127.0.0.3
    private static final String ipv4IpListRegExp            = String.format("^(%s)(,(%s))*$",
            StringUtils.substringBetween(ipv4IpRegExp, "^", "$"), StringUtils.substringBetween(ipv4IpRegExp, "^", "$"));

    // IP1:Port1,IP2:Port2,IP3:Port3 >>>> 127.0.0.1:1024,127.0.0.2:1025,127.0.0.3:65535
    private static final String ipv4IpDynamicPortListRegExp = String.format("^((%s):(%s))(,((%s):(%s)))*$",
            StringUtils.substringBetween(ipv4IpRegExp, "^", "$"),
            StringUtils.substringBetween(ipv4PortRegExp, "^", "$"),
            StringUtils.substringBetween(ipv4IpRegExp, "^", "$"),
            StringUtils.substringBetween(ipv4PortRegExp, "^", "$"));

    public static boolean isIpv4Ip(String ip)
    {
        if (StringUtils.isBlank(ip))
        {
            return false;
        }

        return ip.matches(ipv4IpRegExp);
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

    public static boolean isIpv4PortRegExp(String port)
    {
        if (StringUtils.isBlank(port))
        {
            return true;
        }

        return port.matches(ipv4PortRegExp);
    }

    public static boolean isIpv4DynamicPort(String port)
    {
        if (StringUtils.isBlank(port))
        {
            return true;
        }

        int portNumber = NumberUtils.toInt(port, -1);
        if (portNumber >= 1024 && portNumber <= 65535)
        {
            return true;
        }

        return false;
    }

    public static boolean isIpv4DynamicPortRegExp(String port)
    {
        if (StringUtils.isBlank(port))
        {
            return true;
        }

        return port.matches(ipv4DynamicPortRegExp);
    }

    public static boolean isMask(String mask)
    {
        if (StringUtils.isBlank(mask))
        {
            return false;
        }

        return mask.matches(ipv4MaskRegExp);
    }

    public static boolean isInSameSubnet(String ip1, String ip2, String mask)
    {
        if (!isIpv4Ip(ip1) || !isIpv4Ip(ip2) || !isMask(mask))
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

    public static boolean isMac(String mac)
    {
        if (StringUtils.isBlank(mac))
        {
            return false;
        }

        return mac.matches(macRegExp);
    }

    public static void main(String[] args)
    {
        System.out.println(ipv4IpRegExp);
        System.out.println(ipv4MaskRegExp);
        System.out.println(ipv4PortRegExp);
        System.out.println(ipv4DynamicPortRegExp);
        System.out.println(macRegExp);
        System.out.println(ipv4IpListRegExp);
        System.out.println(ipv4IpDynamicPortListRegExp);

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
        System.out.println(isMask(mask1));
        System.out.println(isMask(mask2));
        System.out.println(isMask(mask3));

        System.out.println(isInSameSubnet("128.128.5.2", "128.128.5.2", "255.255.0.0"));

        // 测试正则表达式和java校验的效率
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            isIpv4DynamicPortRegExp("-1");
            isIpv4DynamicPortRegExp("0");
            isIpv4DynamicPortRegExp("1");
            isIpv4DynamicPortRegExp("999");
            isIpv4DynamicPortRegExp("1023");
            isIpv4DynamicPortRegExp("1024");
            isIpv4DynamicPortRegExp("1025");
            isIpv4DynamicPortRegExp("9999");
            isIpv4DynamicPortRegExp("65534");
            isIpv4DynamicPortRegExp("65535");
            isIpv4DynamicPortRegExp("65536");
            isIpv4DynamicPortRegExp("99999");
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