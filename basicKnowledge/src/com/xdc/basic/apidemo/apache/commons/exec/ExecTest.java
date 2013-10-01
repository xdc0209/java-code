package com.xdc.basic.apidemo.apache.commons.exec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

public class ExecTest
{
	public static void main(String[] args)
	{
		String line = "ping 127.0.0.1 -n 1";
		CommandLine cmdLine = CommandLine.parse(line);

		DefaultExecutor executor = getExecutor();

		ByteArrayOutputStream outAndErr = new ByteArrayOutputStream(); // 设置输出流和错误流
		ByteArrayInputStream input = new ByteArrayInputStream("y".getBytes()); // 设置输入流，比如删除文件要确认，当让这个例子用不到
		executor.setStreamHandler(new PumpStreamHandler(outAndErr, outAndErr, input));

		try
		{
			System.out.println("同步调用开始：");
			int exitValue = executor.execute(cmdLine);
			System.out.println("同步调用结束。");
			System.out.println("同步调用返回值" + exitValue);

			System.out.println("异步调用开始：");
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			executor.execute(cmdLine, resultHandler);
			System.out.println("异步调用结束。");

			// 当前线程阻塞，直到命令行执行完成
			System.out.println("等待异步调用结果");
			resultHandler.waitFor();
			exitValue = resultHandler.getExitValue();
			System.out.println("异步调用返回值" + exitValue);
		}
		catch (ExecuteException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		try
		{
			// 不知道为啥命令行中的字符以GBK编码？ 待研究。
			byte[] buf = outAndErr.toByteArray();
			String utf8String = new String(buf, "UTF-8");
			System.out.println(utf8String);
			String gbkString = new String(buf, "GBK");
			System.out.println(gbkString);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	private static DefaultExecutor getExecutor()
	{
		DefaultExecutor executor = new DefaultExecutor();

		// 设置超时时间，超时后，kill执行命令行的进程，再抛异常ExecuteException
		ExecuteWatchdog watchdog = new ExecuteWatchdog(20 * 1000L);
		executor.setWatchdog(watchdog);

		// 设置期望返回值，不符合抛异常ExecuteException
		executor.setExitValue(0);

		return executor;
	}
}
