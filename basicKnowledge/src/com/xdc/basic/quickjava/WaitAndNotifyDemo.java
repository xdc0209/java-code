package com.xdc.basic.quickjava;

import java.util.Vector;

public class WaitAndNotifyDemo
{
	Vector	data	= new Vector();

	void addData()
	{
		synchronized (data)
		{
			String name = Thread.currentThread().getName();
			System.out.println(name + " enter addData()");
			data.add("value" + ((int) (Math.random() * 100)));
			data.notifyAll();
			System.out.println(name + " data added");
		}
	}

	void removeData()
	{
		synchronized (data)
		{
			String name = Thread.currentThread().getName();
			System.out.println(name + " enter removeData()");

			while (data.size() == 0)
			{
				System.out.println(name + " no data to remove, try to wait");
				try
				{
					data.wait();
					System.out.println(name + " wake up");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			Object o = data.remove(0);
			System.out.println(name + " data removed " + o);
		}
	}

	private void start()
	{
		new Thread2(this).start();
		new Thread2(this).start();
		new Thread2(this).start();
		new Thread1(this).start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		WaitAndNotifyDemo object = new WaitAndNotifyDemo();
		object.start();
	}

	class Thread1 extends Thread
	{
		WaitAndNotifyDemo	synObject;

		Thread1(WaitAndNotifyDemo synObject)
		{
			this.synObject = synObject;
		}

		public void run()
		{
			String name = getName();
			while (true)
			{
				System.out.println(name + " try to addData...");
				synObject.addData();
				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	class Thread2 extends Thread
	{
		WaitAndNotifyDemo	synObject;

		Thread2(WaitAndNotifyDemo synObject)
		{
			this.synObject = synObject;
		}

		public void run()
		{
			String name = getName();
			while (true)
			{
				System.out.println(name + " try to removeData...");
				synObject.removeData();
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
