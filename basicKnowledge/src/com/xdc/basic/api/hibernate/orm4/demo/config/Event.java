package com.xdc.basic.api.hibernate.orm4.demo.config;

import java.util.Date;

public class Event
{
    private Long   id;
    private String title;
    private Date   date;

    public Event()
    {
    }

    public Long getId()
    {
        return id;
    }

    /**
     * 由hibernate自动生成，设置成私有就可以了。
     */
    @SuppressWarnings("unused")
    private void setId(Long id)
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}