package com.xdc.basic.api.apache.commons.lang3.time;

import org.apache.commons.lang3.time.StopWatch;

public class StopWatchTest
{

	public static void main(String[] args)
	{
		/*
		 * NOTE: As from v2.1, the methods protect against inappropriate calls.
		 * Thus you cannot now call stop before start, resume before suspend or
		 * unsplit before split. if not, it throws IllegalStateException
		 * 
		 * 1. split(), suspend(), or stop() cannot be invoked twice 
		 * 2. unsplit() may only be called if the watch has been split() 
		 * 3. resume() may only be called if the watch has been suspend() 
		 * 4. start() cannot be called twice without calling reset()
		 */

		System.out.println("秒表:");
		StopWatch sw = new StopWatch();

		// 计时开始
		sw.start();
		doSomething();
		System.out.println("秒表计时(ms):" + sw.getTime());
		System.out.println("秒表计时(ns):" + sw.getNanoTime());

		// 暂停计时
		sw.suspend();
		doSomething();
		System.out.println("秒表计时(ms):" + sw.getTime());
		System.out.println("秒表计时(ns):" + sw.getNanoTime());

		// 恢复计时
		sw.resume();
		doSomething();
		System.out.println("秒表计时(ms):" + sw.getTime());
		System.out.println("秒表计时(ns):" + sw.getNanoTime());

		// 计时结束
		sw.stop();
		System.out.println("秒表计时(ms):" + sw.getTime());
		System.out.println("秒表计时(ns):" + sw.getNanoTime());
	}

	private static void doSomething()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
