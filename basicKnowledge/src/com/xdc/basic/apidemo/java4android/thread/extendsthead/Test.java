package com.xdc.basic.apidemo.java4android.thread.extendsthead;

class Test
{
	public static void main(String[] args)
	{
		// 生成线程类的对象
		ThreadExt t = new ThreadExt();
		// 启动线程 不能用t.run(); 这样的话，不会启动新的线程还是在当前线程中执行，因为咱们重写的run（）函数和其他的成员函数没有区别
		t.start();
		for (int i = 0; i < 100; i++)
		{
			System.out.println("main-->" + i);
		}
	}
}
