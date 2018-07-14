package com.springinaction.springidol;

@SuppressWarnings("serial")
public class PerformanceException extends RuntimeException
{
    public PerformanceException()
    {
    }

    public PerformanceException(String message)
    {
        super(message);
    }
}
