package com.xdc.basic.skills.encrypt.aes.aes1.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemUtil
{
    public static void exit(int status)
    {
        System.exit(status);
    }

    public static void outPrintln(String x)
    {
        System.out.println(String.format("[%s] %s: %s", getTime(), "INFO", x));
    }

    public static void errPrintln(String x)
    {
        System.err.println(String.format("[%s] %s: %s", getTime(), "ERROR", x));
    }

    public static void errPrintln(String x, Throwable e)
    {
        System.err.println(String.format("[%s] %s: %s", getTime(), "ERROR", x));
        System.err.println(getStackTrace(e));
    }

    private static String getStackTrace(Throwable e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    private static String getTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date());
    }
}
