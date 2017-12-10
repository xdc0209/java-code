package com.xdc.basic.api.apache.commons.lang3.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionUtilsTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        try
        {
            throw new Exception("ERROR");
        }
        catch (Throwable e)
        {
            // 原始方法
            e.getMessage();
            e.toString(); // 内部调用了 e.getMessage();
            e.printStackTrace(); // 内部调用了 e.toString();

            // 原始方法获得rootCause
            Throwable rootCause = getRootCause(e);

            // 下面为apache包装类，内部实现调用的原始方法
            String message = ExceptionUtils.getMessage(e);
            String stackTrace = ExceptionUtils.getStackTrace(e);

            String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
            String[] rootCauseStackTrace = ExceptionUtils.getRootCauseStackTrace(e);

            // apache包装类获得rootCause
            rootCause = ExceptionUtils.getRootCause(e);
        }
    }

    /**
     * 原始方法获得rootCause
     */
    private static Throwable getRootCause(Throwable e)
    {
        while (e.getCause() != null)
        {
            e = e.getCause();
        }
        return e;
    }
}
