package com.xdc.basic.api.thread.lock.splitinglock;

import java.util.HashMap;
import java.util.Map;

public class BankService
{
    private Map<String, User>    bank = new HashMap<String, User>();
    private SplitingLock<String> lock = new SplitingLock<String>();

    public BankService()
    {
        super();
    }

    public Map<String, User> getBank()
    {
        return bank;
    }

    /**
     * 存钱
     * 
     * @param money
     */
    public void deposit(String userId, int money)
    {
        lock.lock(userId);
        try
        {
            User user = bank.get(userId);
            if (user == null)
            {
                user = new User(userId, 0);
                bank.put(userId, user);
            }

            System.out.println(Thread.currentThread().getName() + " : " + userId + " 开始存入 " + money + "元。当前值：" + user);
            user.deposit(money);
            System.out.println(Thread.currentThread().getName() + " : " + userId + " 完成存入 " + money + "元。当前值：" + user);

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            lock.unlock(userId);
        }
    }

    /**
     * 取钱
     * 
     * @param money
     */
    public void withdrawing(String userId, int money)
    {
        lock.lock(userId);
        try
        {
            User user = bank.get(userId);
            if (user == null)
            {
                user = new User(userId, 0);
                bank.put(userId, user);
            }

            System.out.println(Thread.currentThread().getName() + " : " + userId + " 开始取出 " + money + "元。当前值：" + user);
            user.withdrawing(money);
            System.out.println(Thread.currentThread().getName() + " : " + userId + " 开始取出 " + money + "元。当前值：" + user);

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            lock.unlock(userId);
        }
    }
}
