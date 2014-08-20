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
            String message = ExceptionUtils.getMessage(e);
            String stackTrace = ExceptionUtils.getStackTrace(e);

            e.getMessage();
            e.printStackTrace();
            e.toString();
            while (e.getCause() != null)
            {
                e = e.getCause();
            }
            e.getMessage();
            e.printStackTrace();
            e.toString();
        }
    }
}
