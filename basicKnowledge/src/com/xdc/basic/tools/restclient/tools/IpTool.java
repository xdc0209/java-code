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
    }
}