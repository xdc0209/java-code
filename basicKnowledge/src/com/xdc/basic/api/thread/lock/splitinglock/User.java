package com.xdc.basic.api.thread.lock.splitinglock;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User
{
    private String userId;
    private long   money;

    public User()
    {
        super();
    }

    public User(String userId, long money)
    {
        super();
        this.userId = userId;
        this.money = money;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public long getMoney()
    {
        return money;
    }

    public void setMoney(long money)
    {
        this.money = money;
    }

    /**
     * 存钱
     * 
     * @param money
     */
    public void deposit(int money)
    {
        this.money += money;
    }

    /**
     * 取钱
     * 
     * @param money
     */
    public void withdrawing(int money)
    {
        this.money -= money;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
