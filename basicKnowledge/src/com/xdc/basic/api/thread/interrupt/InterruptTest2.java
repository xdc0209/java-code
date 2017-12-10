package com.xdc.basic.api.thread.interrupt;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InterruptTest2 implements Runnable
{
    public static void main(String[] args) throws InterruptedException
    {
        // 参考：http://www.infoq.com/cn/articles/java-interrupt-mechanism
        //
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
        //
        // 与线程中断相关的所有方法
        // boolean java.lang.Thread.isInterrupted() 判断线程对象是否处于中断(或者说中断变量是否为true)
        // boolean java.lang.Thread.interrupted() 判断线程对象是否处于中断(或者说中断变量是否为true)，并清除中断，使线程处于非中断状态(或者说将中断变量置为false)。静态方法interrupted会将当前线程的中断状态清除，但这个方法的命名极不直观，很容易造成误解，需要特别注意。一般的处理中断的策略是：在代码的合适位置(while死循环中的某处)，判断中断状态，如果处于中断，则先要清除中断，再对此次中断做对应的逻辑处理(如终止while死循环)。清除中断的原因是避免外界的一次异常中断信号，触发本线程的多次处理。
        // void java.lang.Thread.interrupt() 触发线程对象的中断，即使线程处于中断状态(或者说将中断变量置为true)
        //
        // 使用场景
        // 点击某个桌面应用中的取消按钮时；
        // 某个操作超过了一定的执行时间限制需要中止时；
        // 多个线程做相同的事情，只要一个线程成功其它线程都可以取消时；
        // 一组线程中的一个或多个出现错误导致整组都无法继续时；
        // 当一个应用或服务需要停止时。
        //
        // 处理时机
        // Java中断机制是一种协作机制，也就是说通过中断并不能直接终止另一个线程，而需要被中断的线程自己处理中断。
        // 显然，作为一种协作机制，不会强求被中断线程一定要在某个点进行处理。实际上，被中断线程只需在合适的时候处理即可，如果没有合适的时间点，甚至可以不处理，这时候在任务处理层面，就跟没有调用中断方法一样。“合适的时候”与线程正在处理的业务逻辑紧密相关，例如，每次迭代的时候，进入一个可能阻塞且无法中断的方法之前等，但多半不会出现在某个临界区更新另一个对象状态的时候，因为这可能会导致对象处于不一致状态。
        // 处理时机决定着程序的效率与中断响应的灵敏性。频繁的检查中断状态可能会使程序执行效率下降，相反，检查的较少可能使中断请求得不到及时响应。如果发出中断请求之后，被中断的线程继续执行一段时间不会给系统带来灾难，那么就可以将中断处理放到方便检查中断，同时又能从一定程度上保证响应灵敏度的地方。当程序的性能指标比较关键时，可能需要建立一个测试模型来分析最佳的中断检测点，以平衡性能和响应灵敏性。

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
            Thread.currentThread().interrupt(); // 因为此时isInterrupted的状态时false，所以这句话就特别重要了。要不然下一个for循环的b.take就无法进行下去了
            System.out.println(Thread.currentThread().isInterrupted()); // 现在就是true了
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
