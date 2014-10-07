package com.xdc.basic.tools.restframework.message.music.get;

import java.lang.Integer;
import java.lang.String;

public class Album
{
    private String cover;

    private Integer id;

    private String name;

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
