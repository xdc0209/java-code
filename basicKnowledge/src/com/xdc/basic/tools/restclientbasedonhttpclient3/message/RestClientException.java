package com.xdc.basic.tools.restclientbasedonhttpclient3.message;

public class RestClientException extends Exception
{
    private static final long serialVersionUID = -2039474965099519953L;

    public RestClientException()
    {
        super();
    }

    public RestClientException(String message)
    {
        super(message);
    }

    public RestClientException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RestClientException(Throwable cause)
    {
        super(cause);
    }
}
