package com.xdc.basic.tools.restframework.message;

public class CallerException extends Exception
{
    private static final long serialVersionUID = -2039474965099519953L;

    public CallerException()
    {
        super();
    }

    public CallerException(String message)
    {
        super(message);
    }

    public CallerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CallerException(Throwable cause)
    {
        super(cause);
    }
}
