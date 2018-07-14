package com.xdc.basic.tools.sendjars;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

public class ExecutorTool
{
    private DefaultExecutor       executor;
    private ByteArrayOutputStream out;            // 设置输出流
    private ByteArrayOutputStream err;            // 设置输出流
    @SuppressWarnings("unused")
    private ByteArrayInputStream  input;          // 设置输入流，比如删除文件要确认，当让这个例子用不到

    private List<String>          lastOutputLines;
    private List<String>          lastErrLines;

    public ExecutorTool()
    {
        super();
        this.out = new ByteArrayOutputStream(); // 设置输出流
        this.err = new ByteArrayOutputStream(); // 设置输出流
        this.input = null; // 设置输入流，比如删除文件要确认，当然这个例子用不到 TODO 以后在考虑输入的问题吧

        this.lastOutputLines = new ArrayList<String>();
        this.lastErrLines = new ArrayList<String>();

        this.executor = new DefaultExecutor();

        // 设置超时时间，超时后，kill执行命令行的进程，再抛异常ExecuteException
        ExecuteWatchdog watchdog = new ExecuteWatchdog(20 * 1000L);
        executor.setWatchdog(watchdog);

        // 设置期望返回值，不符合抛异常ExecuteException
        executor.setExitValue(0);

        executor.setStreamHandler(new PumpStreamHandler(out, err, null));
    }

    public int execute(CommandLine cmdLine)
    {
        int excute = -1;
        try
        {
            excute = executor.execute(cmdLine);
            this.lastOutputLines = IOUtils.readLines(new ByteArrayInputStream(this.out.toByteArray()), "UTF-8");
            out.reset();
        }
        catch (ExecuteException e)
        {
            handleException(e);
        }
        catch (IOException e)
        {
            handleException(e);
        }

        return excute;
    }

    private void handleException(Exception e)
    {
        e.printStackTrace();
        try
        {
            this.lastErrLines = IOUtils.readLines(new ByteArrayInputStream(this.err.toByteArray()), "UTF-8");
            err.reset();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    public List<String> getLastOutputLines()
    {
        return lastOutputLines;
    }

    public List<String> getLastErrLines()
    {
        return lastErrLines;
    }
}
