package com.xdc.basic.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil
{
    /**
     * 将CheckedException转换为UncheckedException。
     * 参考：com.google.common.base.Throwables.propagate(Throwable)
     * 参考：com.jeesite.common.lang.ExceptionUtils.unchecked(Exception)
     */
    public static RuntimeException unchecked(Throwable throwable)
    {
        if (throwable == null)
        {
            throw new NullPointerException();
        }

        if (throwable instanceof RuntimeException)
        {
            return (RuntimeException) throwable;
        }

        return new RuntimeException(throwable);
    }

    public static RuntimeException newRuntimeException(Throwable throwable, String format, Object... args)
    {
        return new RuntimeException(String.format(format, args), throwable);
    }

    public static String getStackTrace(Throwable e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
