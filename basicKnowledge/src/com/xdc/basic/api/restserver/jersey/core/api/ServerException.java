package com.xdc.basic.api.restserver.jersey.core.api;

public class ServerException extends Exception
{
    private static final long serialVersionUID = -3372468102560826502L;

    public ServerException()
    {
    }

    public ServerException(String message)
    {
        super(message);
    }

    public ServerException(Throwable cause)
    {
        super(cause);
    }

    public ServerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}