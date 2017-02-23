package com.xdc.basic.skills.encrypt.aes.aes1.core;

public class EncException extends Exception
{
    private static final long serialVersionUID = 1668273898739816801L;

    public EncException()
    {
        super();
    }

    public EncException(String message)
    {
        super(message);
    }

    public EncException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EncException(Throwable cause)
    {
        super(cause);
    }
}