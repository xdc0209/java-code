package com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials;

/**
 * 客户/用户身份凭证。
 */
public class Credentials implements Cloneable
{
    enum Status
    {
        Normal, ImmediateDestroy, ExpiredDestroy
    }

    /**
     * 获取客户/用户身份标识。
     */
    private String accessKey;

    /**
     * 获取客户/用户身份密钥。已经过bash64编码。
     */
    private String secretKey;

    /**
     * 创建时间，用户过期销毁。
     */
    private long   createTime;

    /**
     * 凭证有效期，默认10年。
     */
    private long   validityPeriod;

    /**
     * 当前状态。
     */
    private Status status;

    public Credentials(String accessKey, String secretKey)
    {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.createTime = System.currentTimeMillis();
        this.validityPeriod = 10 * 365 * 24 * 60 * 60 * 1000;
        this.status = Status.Normal;
    }

    public String getAccessKey()
    {
        return accessKey;
    }

    public void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }

    public String getSecretKey()
    {
        return secretKey;
    }

    public void setSecretKey(String secretKey)
    {
        this.secretKey = secretKey;
    }

    public long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }

    public long getValidityPeriod()
    {
        return validityPeriod;
    }

    public void setValidityPeriod(long validityPeriod)
    {
        this.validityPeriod = validityPeriod;
    }

    public Status getStatus()
    {
        refreshStatus();
        return status;
    }

    public void setStatus(Status status)
    {
        if (this.status == Status.Normal && status == Status.ImmediateDestroy)
        {
            this.status = status;
        }
    }

    public void refreshStatus()
    {
        if (status == Status.Normal)
        {
            if (System.currentTimeMillis() - createTime > validityPeriod)
            {
                status = Status.ExpiredDestroy;
            }
        }
    }

    public Credentials clone() throws CloneNotSupportedException
    {
        return (Credentials) super.clone();
    }
}
