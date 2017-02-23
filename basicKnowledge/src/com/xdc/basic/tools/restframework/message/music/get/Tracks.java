package com.xdc.basic.tools.restframework.message.music.get;

import java.util.List;

public class Tracks
{
    private Album         album;

    private List<Artists> artists;

    private String        availability;

    private Integer       id;

    private List<Medias>  medias;

    private Integer       mv;

    private String        slyric;

    private String        title;

    private String        isdown;

    private String        isplay;

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum(Album album)
    {
        this.album = album;
    }

    public List<Artists> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artists> artists)
    {
        this.artists = artists;
    }

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Medias> getMedias()
    {
        return medias;
    }

    public void setMedias(List<Medias> medias)
    {
        this.medias = medias;
    }

    public Integer getMv()
    {
        return mv;
    }

    public void setMv(Integer mv)
    {
        this.mv = mv;
    }

    public String getSlyric()
    {
        return slyric;
    }

    public void setSlyric(String slyric)
    {
        this.slyric = slyric;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIsdown()
    {
        return isdown;
    }

    public void setIsdown(String isdown)
    {
        this.isdown = isdown;
    }

    public String getIsplay()
    {
        return isplay;
    }

    public void setIsplay(String isplay)
    {
        this.isplay = isplay;
    }

}
