package com.xdc.basic.api.thread.basic.extendsthead;

class Test
{
    public static void main(String[] args)
    {
        // 生成线程类的对象。
        ThreadExt t = new ThreadExt();
        // 启动线程 不能用t.run(); 这样的话，不会启动新的线程还是在当前线程中执行，因为咱们重写的run（）函数和其他的成员函数没有区别。
        // 虽然run()函数实现了多个线程的并行处理，但我们不能直接调用run()函数，而是通过调用start()函数来调用run()函数。在调用start()的时候，start()函数会首先进行与多线程相关的初始化（这也是为什么不能直接调用run()函数的原因），然后再调用run()函数。
        t.start();
        for (int i = 0; i < 100; i++)
        {
            System.out.println("main-->" + i);
        }
    }
}
