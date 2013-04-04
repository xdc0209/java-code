package com.xdc.basic.quickjava.thread;

/**
 * @see Java书上 P312页 运用多线程技术解决“生产者&消费者”问题
 * @version 1.0
 * @author 小朋
 * 
 */

class CubbyHole
{
	private int	    content;

	private boolean	available	= false;

	public synchronized int get()
	{
		while (available == false)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		available = false;
		notifyAll();
		System.out.println("Consumer " + "got: " + content);
		return content;
	}

	public synchronized void put(int value)
	{
		while (available == true)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		content = value;
		available = true;
		notifyAll();
		System.out.println("Producer " + "put: " + content);
	}
}

class Producer extends Thread
{
	private CubbyHole	cubbyhole;

	public Producer(CubbyHole c)
	{
		cubbyhole = c;
	}

	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			cubbyhole.put(i);
			try
			{
				sleep((int) Math.random() * 1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread
{
	private CubbyHole	cubbyhole;

	public Consumer(CubbyHole c)
	{
		cubbyhole = c;
	}

	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			cubbyhole.get();
		}
	}
}

public class PudocterAndConsumer
{
	public static void main(String[] args)
	{
		CubbyHole h = new CubbyHole();
		Producer producer = new Producer(h);
		Consumer consumer = new Consumer(h);
		producer.start();
		consumer.start();
	}
}
