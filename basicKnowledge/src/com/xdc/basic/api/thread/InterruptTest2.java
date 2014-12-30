package com.xdc.basic.api.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InterruptTest2 implements Runnable
{
    public static void main(String[] args) throws InterruptedException
    {
        // 最近写了些多线程的程序，用Thread.sleep()的时候有时会碰到InterruptedException。查了一些资料，下面是我自己的一些理解。
        //
        // 阻塞方法
        // 一些多线程相关的方法是阻塞方法，比如Thread.sleep(), Thread.wait(), Thread.join()。
        // 这些方法的执行通常需要比较长的时间来完成，当代码执行到阻塞方法时，一般要等待该方法返回后
        // 才能继续往下执行，而InterruptedException提供了一种特殊的机制提前结束阻塞方法。
        //
        // 中断变量
        // 每个线程都会维护一个bool变量，表示线程处于中断（true）或者非中断状态(false)。在线程初始的情况下中断变量为false。
        // 这个变量的bool值可以通过Thread.isInterrupted()方法来读取，通过Thread.interrupted()方法来清除中断（即将中断变量置为false）。
        //
        // 线程中断
        // 一个线程可以通过调用Thread.interrupt()方法来中断另外一个线程，具体过程如下：
        // 1. 中断变量被设置为true。
        // 2. 如果线程执行到了阻塞方法，那么该方法取消阻塞，并将中断变量重新置为false。
        // （这种机制是通过阻塞方法内部不断轮询中断变量的值来实现的）

        Thread h = new Thread(new InterruptTest2());
        h.start();
        h.interrupt();

        System.out.println(h.isInterrupted());
    }

    @Override
    public void run()
    {
        BlockingQueue<Object> b = new ArrayBlockingQueue<Object>(10);

        while (!Thread.currentThread().isInterrupted())
        {
            // 中断变量的值是否默认为false
        }
        System.out.println("走出死循环");

        try
        {
            while (true)
            {
                b.take();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("遇到阻塞1");
            System.out.println(Thread.currentThread().isInterrupted()); // 可以看出来此时的答案是false
            Thread.currentThread().interrupt();                         // 因为此时isInterrupted的状态时false，所以这句话就特别重要了。要不然下一个for循环的b.take就无法进行下去了
            System.out.println(Thread.currentThread().isInterrupted()); // 现在就是true了。
        }

        try
        {
            while (true)
            {
                b.take();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("遇到阻塞2");
        }
        System.out.println("处理完事儿---");
    }
}
