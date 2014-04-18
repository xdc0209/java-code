package com.xdc.basic.api.thread.lockfree.databuffer;

import java.util.Vector;

import org.apache.commons.lang3.RandomStringUtils;

public class DataBufferSync
{
    Vector<String> dataBuffer = new Vector<String>(); // 数据缓冲区
    final int      MAX_SIZE   = 3;                   // 设置缓冲区大小

    void addData()
    {
        synchronized (dataBuffer)
        {
            String name = Thread.currentThread().getName();
            System.out.println(name + " enter addData()");

            while (dataBuffer.size() == MAX_SIZE)
            {
                try
                {
                    System.out.println(name + " data buffer is full, try to wait");
                    dataBuffer.wait();
                    System.out.println(name + " wake up, continue to add data");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            String data = "value-" + RandomStringUtils.randomNumeric(2);
            dataBuffer.add(data);
            dataBuffer.notifyAll();

            System.out.println(name + " adds " + data + ", ramains " + dataBuffer.size());
            System.out.println(name + " leave addData()");
        }
    }

    void removeData()
    {
        synchronized (dataBuffer)
        {
            String name = Thread.currentThread().getName();
            System.out.println(name + " enter removeData()");

            while (dataBuffer.size() == 0)
            {
                try
                {
                    // 临时释放锁,并阻塞当前线程,好让其他使用同一把锁的线程有机会执行.
                    // 其他线程在执行到一定地方后用notify()通知wait()的线程,
                    // 待notify()所在的同步块运行完之后,wait所在的线程就可以继续执行.
                    System.out.println(name + " data buffer is empty, try to wait");
                    dataBuffer.wait();
                    System.out.println(name + " wake up, continue to remove data");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            String data = dataBuffer.remove(0);
            dataBuffer.notifyAll();

            System.out.println(name + " removes " + data + ", ramains " + dataBuffer.size());
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
        DataBufferSync wn = new DataBufferSync();
        wn.start();
    }

    class Producer extends Thread
    {
        DataBufferSync synObject;

        Producer(DataBufferSync synObject)
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
        DataBufferSync synObject;

        Consumer(DataBufferSync synObject)
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
