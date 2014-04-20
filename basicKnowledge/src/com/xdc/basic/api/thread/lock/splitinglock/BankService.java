package com.xdc.basic.api.thread.lock.splitinglock;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BankService
{
    private Map<String, User>    bank = new HashMap<String, User>();
    private SplitingLock<String> lock = new SplitingLock<String>();

    public BankService()
    {
        super();
        bank.put("xdc", new User("xdc", 0));
        bank.put("cc", new User("cc", 0));
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
            user.deposit(money);
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
            user.withdrawing(money);
        }
        finally
        {
            lock.unlock(userId);
        }
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
