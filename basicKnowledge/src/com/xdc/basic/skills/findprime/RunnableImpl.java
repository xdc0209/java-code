package com.xdc.basic.skills.findprime;

public class RunnableImpl implements Runnable
{
    Element element = null;

    public RunnableImpl(Element element)
    {
        super();
        this.element = element;
    }

    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + " 开始");
        for (int i = element.getBeginNum(); i < element.getEndNum(); i++)
        {
            if (isPrime(i))
            {
                element.getPrimeArrayList().add(i);
            }
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

    private boolean isPrime(int n)
    {
        if ((n > 2 && n % 2 == 0) || (n < 2))
        {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n) + 0.1; i = i + 2)
        {
            if (n % i == 0)
            {
                return false;
            }
        }
        return true;
    }
}
