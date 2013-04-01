package com.xdc.basic.java4android.thread.implrunable;

class RunnableImpl implements Runnable
{
	int	i	= 100;

	public void run()
	{
		while (true)
		{
			System.out.println(Thread.currentThread().getName() + "-->" + i);
			i--;
			Thread.yield();
			if (i < 0)
			{
				break;
			}
		}
	}
}
