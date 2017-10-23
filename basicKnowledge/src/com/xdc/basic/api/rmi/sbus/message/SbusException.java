package com.xdc.basic.api.rmi.sbus.message;

public class SbusException extends Exception
{
    private static final long serialVersionUID = -2039474965099519953L;

    public SbusException()
    {
        super();
    }

    public SbusException(String message)
    {
        super(message);
    }

    public SbusException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SbusException(Throwable cause)
    {
        super(cause);
    }
}
