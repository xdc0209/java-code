package com.xdc.basic.tools.restclient.tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class IpTool
{
    public static boolean isIpv4Ip(String ip)
    {
        if (StringUtils.isBlank(ip))
        {
            return false;
        }

        String ipRegExp = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

        return ip.matches(ipRegExp);
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

    public static boolean isMask(String mask)
    {
        if (StringUtils.isBlank(mask))
        {
            return false;
        }

        String maskRegExp = "^((254|252|248|240|224|192|128|0)\\.0\\.0\\.0)$|^(255\\.(254|252|248|240|224|192|128|0)\\.0\\.0)$|^(255\\.255\\.(254|252|248|240|224|192|128|0)\\.0)$|^(255\\.255\\.255\\.(254|252|248|240|224|192|128|0))$";

        return mask.matches(maskRegExp);
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

    public static void main(String[] args)
    {
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
    }
}