package com.xdc.basic.tools.restframework.message.music.get;

public class Artists
{
    private Integer id;

    private String  name;

    private Integer num_albums;

    private Integer num_tracks;

    private String  portrait;

    private Boolean valid;

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

    public Integer getNum_albums()
    {
        return num_albums;
    }

    public void setNum_albums(Integer num_albums)
    {
        this.num_albums = num_albums;
    }

    public Integer getNum_tracks()
    {
        return num_tracks;
    }

    public void setNum_tracks(Integer num_tracks)
    {
        this.num_tracks = num_tracks;
    }

    public String getPortrait()
    {
        return portrait;
    }

    public void setPortrait(String portrait)
    {
        this.portrait = portrait;
    }

    public Boolean getValid()
    {
        return valid;
    }

    public void setValid(Boolean valid)
    {
        this.valid = valid;
    }
}
