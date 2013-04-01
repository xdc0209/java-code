package com.xdc.basic.java4android.thread.implrunable;

class Test
{
	public static void main(String[] args)
	{
		// 生成一个Runnable接口的实现类的对象
		RunnableImpl r = new RunnableImpl();
		// 生成一个Thread对象，并将Runnalbe接口的实
		// 现类的对象作为参数传递给该Thread对象

		Thread t = new Thread(r);
		t.setName("t1");
		t.start();

		Thread t2 = new Thread(r);
		t2.setName("t2");
		t2.start();
	}
}
