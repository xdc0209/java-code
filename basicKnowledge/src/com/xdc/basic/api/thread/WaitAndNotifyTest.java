package com.xdc.basic.api.thread;

import java.util.Vector;

import org.apache.commons.lang3.RandomStringUtils;

public class WaitAndNotifyTest
{
    Vector<String> data = new Vector<String>();

    void addData()
    {
        synchronized (data)
        {
            String name = Thread.currentThread().getName();
            System.out.println(name + " enter addData()");

            String s = "value-" + RandomStringUtils.randomNumeric(2);
            data.add(s);
            data.notifyAll();

            System.out.println(name + " adds " + s + ", ramains " + data.size());
            System.out.println(name + " leave addData()");
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
                    // 临时释放锁,并阻塞当前线程,好让其他使用同一把锁的线程有机会执行.
                    // 其他线程在执行到一定地方后用notify()通知wait()的线程,
                    // 待notify()所在的同步块运行完之后,wait所在的线程就可以继续执行.
                    data.wait();
                    System.out.println(name + " wake up");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            String s = data.remove(0);

            System.out.println(name + " removes " + s + ", ramains " + data.size());
            System.out.println(name + " leave removeData()");
        }
    }

    private void start()
    {
        new Consumer(this).start();
        new Consumer(this).start();
        new Consumer(this).start();
        new Producer(this).start();
    }

    public static void main(String[] args)
    {
        WaitAndNotifyTest wn = new WaitAndNotifyTest();
        wn.start();
    }

    class Producer extends Thread
    {
        WaitAndNotifyTest synObject;

        Producer(WaitAndNotifyTest synObject)
        {
            this.synObject = synObject;
        }

        public void run()
        {
            String name = getName();
            while (true)
            {
                System.out.println(name + " try to add Data ...");
                synObject.addData();
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
    }

    class Consumer extends Thread
    {
        WaitAndNotifyTest synObject;

        Consumer(WaitAndNotifyTest synObject)
        {
            this.synObject = synObject;
        }

        public void run()
        {
            String name = getName();
            while (true)
            {
                System.out.println(name + " try to remove Data ...");
                synObject.removeData();
                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
