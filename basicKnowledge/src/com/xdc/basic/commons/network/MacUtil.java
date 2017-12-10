package com.xdc.basic.commons.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import com.xdc.basic.commons.StringUtil;

public class MacUtil
{
    // Mac 示例：00:0C:29:C2:A9:A0
    private static final String       macRegExp   = "^([a-fA-F0-9]{2}[-:]){5}([a-fA-F0-9]){2}$";

    // 特殊的Mac
    private static final List<String> specialMacs = new ArrayList<String>();

    static
    {
        // 全0的mac一般有两个原因：
        // 1.网卡绑定配置出错。
        // 2.服务器预置了多个网卡，但是根据业务规划没有全部使用，为了节约成本，可以在出厂时要求部分网卡不烧录，这些未烧录的网卡mac就是全为0的。
        specialMacs.add("00:00:00:00:00:00");
        specialMacs.add("00-00-00-00-00-00");
    }

    public static boolean isMac(String mac)
    {
        return isMac(mac, true);
    }

    public static boolean isMac(String mac, boolean filterSpecialMac)
    {
        if (!StringUtil.matches(mac, macRegExp))
        {
            return false;
        }

        if (filterSpecialMac && specialMacs.contains(mac))
        {
            return false;
        }

        return true;
    }

    public static List<String> getMacs() throws SocketException
    {
        return getMacs(true);
    }

    /**
     * 参考：http://www.jb51.net/article/93343.htm
     * 参考：http://www.cnblogs.com/KnowledgeShare/p/6184383.html
     */
    public static List<String> getMacs(boolean filterSpecialMac) throws SocketException
    {
        // 使用TreeSet：保证唯一，保证升序。
        TreeSet<String> macTreeSet = new TreeSet<String>();

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements())
        {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isLoopback() || networkInterface.isVirtual())
            {
                continue;
            }

            byte[] macBytes = networkInterface.getHardwareAddress();
            if (ArrayUtils.isEmpty(macBytes))
            {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < macBytes.length; i++)
            {
                sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
            }
            String mac = sb.toString();

            if (filterSpecialMac && specialMacs.contains(mac))
            {
                continue;
            }

            macTreeSet.add(mac);
        }

        List<String> macList = new ArrayList<String>();
        for (String mac : macTreeSet)
        {
            macList.add(mac);
        }

        return macList;
    }
}
