package com.xdc.basic.commons.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.xdc.basic.commons.StringUtil;

public class IpUtil
{
    // IP 示例：127.0.0.1
    private static final String ipv4IpRegex                = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

    // Mask 示例：255.255.0.0
    private static final String ipv4MaskRegex              = "^((254|252|248|240|224|192|128|0)\\.0\\.0\\.0)$|^(255\\.(254|252|248|240|224|192|128|0)\\.0\\.0)$|^(255\\.255\\.(254|252|248|240|224|192|128|0)\\.0)$|^(255\\.255\\.255\\.(254|252|248|240|224|192|128|0))$";

    // Port 范围：0-65535
    private static final String ipv4PortRegex              = "^[0-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

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
        return StringUtil.matches(ip, ipv4IpRegex);
    }

    public static boolean isIpv4Mask(String mask)
    {
        return StringUtil.matches(mask, ipv4MaskRegex);
    }

    public static boolean isIpv4Port(String port)
    {
        int portNumber = NumberUtils.toInt(port, -1);
        return (portNumber >= 0 && portNumber <= 65535);
    }

    public static boolean isIpv4PortRegex(String port)
    {
        return StringUtil.matches(port, ipv4PortRegex);
    }

    public static boolean isIpv4DynamicPort(String dynamicPort)
    {
        int portNumber = NumberUtils.toInt(dynamicPort, -1);
        return (portNumber >= 1024 && portNumber <= 65535);
    }

    public static boolean isIpv4DynamicPortRegex(String dynamicPort)
    {
        return StringUtil.matches(dynamicPort, ipv4DynamicPortRegex);
    }

    public static boolean isIpv4IpPort(String ipPort)
    {
        return StringUtil.matches(ipPort, ipv4IpPortRegex);
    }

    public static boolean isIpv4IpDynamicPort(String ipDynamicPort)
    {
        return StringUtil.matches(ipDynamicPort, ipv4IpDynamicPortRegex);
    }

    public static boolean isIpv4IpList(String ipList)
    {
        return StringUtil.matches(ipList, ipv4IpListRegex);
    }

    public static boolean isIpv4IpPortList(String ipPortList)
    {
        return StringUtil.matches(ipPortList, ipv4IpPortListRegex);
    }

    public static boolean isIpv4IpDynamicPortList(String ipDynamicPortList)
    {
        return StringUtil.matches(ipDynamicPortList, ipv4IpDynamicPortListRegex);
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

    /**
     * 参考：http://www.jb51.net/article/93343.htm
     * 参考：http://www.cnblogs.com/KnowledgeShare/p/6184383.html
     */
    public static List<String> getIps() throws SocketException
    {
        // 使用TreeSet：保证唯一，保证升序。
        TreeSet<String> ipTreeSet = new TreeSet<String>();

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements())
        {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp())
            {
                continue;
            }

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements())
            {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!(inetAddress instanceof Inet4Address))
                {
                    continue;
                }

                ipTreeSet.add(inetAddress.getHostAddress());
            }
        }

        List<String> ipList = new ArrayList<String>();
        for (String ip : ipTreeSet)
        {
            ipList.add(ip);
        }

        return ipList;
    }
}
