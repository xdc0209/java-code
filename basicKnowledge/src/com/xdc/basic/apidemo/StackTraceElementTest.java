package com.xdc.basic.apidemo;

/**
 * java栈跟踪元素(StackTraceElement)用法示例
 * 
 * @author xdc
 * 
 */

/*
 * 1.获取当前运行代码的类名，方法名，行号，主要是通过java.lang.StackTraceElement类
 * 
 * 2. [1]获得调用者的方法名, 同new Throwable String _methodName = new
 * Exception().getStackTrace()[1].getMethodName(); [0]获得当前的方法名, 同new Throwable
 * String _thisMethodName = new Exception().getStackTrace()[0].getMethodName();
 */

/*
 * 原理：使用getStackTrace()查明异常, 它位于Throwable类中。
 * getStackTrace()返回Throwable对象被创建时所创建的StackTraceElement对象的一个数组。
 * 查询这些StackTraceElements可以准确地找出错误发生在什么地方。 对于调用getStackTrace()
 * 时被调用的每个方法，返回的数组包含与它们相应的一个元素，另外返回的数组还包含一个元素来表明发出调用的代码。 数组的第一个元素指出抛出最初异常的方法。
 */
public class StackTraceElementTest
{

	public static void main(String[] args)
	{
		System.out.println("line1: " + new Throwable().getStackTrace()[0].getLineNumber());
		System.out.println("line2: " + getLineInfo());
		System.out.println("line3: " + getTraceInfo());

		// output all related info of the existing stack traces
		StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
		int steArrayLength = steArray.length;
		if (steArrayLength == 0)
		{
			System.err.println("No Stack Trace.");
		}
		else
		{
			for (int i = 0; i < steArrayLength; i++)
			{
				System.out.println("Stack Trace-" + i);
				StackTraceElement ste = steArray[i];
				String s = ste.getFileName() + ": Line " + ste.getLineNumber();
				System.out.println(s);
			}
		}
	}

	public static String getTraceInfo()
	{
		StringBuffer sb = new StringBuffer();

		// 返回的数据组大小为2： [0] getTraceInfo(), [1] main()
		StackTraceElement[] stacks = new Throwable().getStackTrace();

		sb.append("class: ").append(stacks[1].getClassName()).append("; method: ").append(stacks[1].getMethodName())
		        .append("; number: ").append(stacks[1].getLineNumber());

		return sb.toString();
	}

	public static String getLineInfo()
	{
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getFileName() + ": Line " + ste.getLineNumber();
	}
}