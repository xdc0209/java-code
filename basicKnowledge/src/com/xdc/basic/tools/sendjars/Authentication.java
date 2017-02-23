package com.xdc.basic.tools.sendjars;

public class Authentication
{
    private String ip;

    private String user;

    private String password;

    public Authentication(String ip, String user, String password)
    {
        super();
        this.ip = ip;
        this.user = user;
        this.password = password;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
