package com.xdc.basic.tools.restframework.message.music.get;

import java.util.List;

public class Albums
{
    private List<Artists> artists;

    private Boolean       available;

    private String        company;

    private String        cover;

    private Integer       id;

    private String        name;

    private Integer       num_tracks;

    private String        release_date;

    private String        type;

    public List<Artists> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artists> artists)
    {
        this.artists = artists;
    }

    public Boolean getAvailable()
    {
        return available;
    }

    public void setAvailable(Boolean available)
    {
        this.available = available;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

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

    public Integer getNum_tracks()
    {
        return num_tracks;
    }

    public void setNum_tracks(Integer num_tracks)
    {
        this.num_tracks = num_tracks;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
