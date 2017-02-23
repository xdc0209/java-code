package com.xdc.basic.tools.restframework.message.music.get;

import java.util.List;

public class GetMusicResponse
{
    private Integer       album_offset;

    private List<Albums>  albums;

    private Integer       artist_offset;

    private List<Artists> artists;

    private Integer       dm_error;

    private String        error_msg;

    private Integer       recommend;

    private Integer       total_albums;

    private Integer       total_artists;

    private Integer       total_tracks;

    private Integer       track_offset;

    private List<Tracks>  tracks;

    public Integer getAlbum_offset()
    {
        return album_offset;
    }

    public void setAlbum_offset(Integer album_offset)
    {
        this.album_offset = album_offset;
    }

    public List<Albums> getAlbums()
    {
        return albums;
    }

    public void setAlbums(List<Albums> albums)
    {
        this.albums = albums;
    }

    public Integer getArtist_offset()
    {
        return artist_offset;
    }

    public void setArtist_offset(Integer artist_offset)
    {
        this.artist_offset = artist_offset;
    }

    public List<Artists> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artists> artists)
    {
        this.artists = artists;
    }

    public Integer getDm_error()
    {
        return dm_error;
    }

    public void setDm_error(Integer dm_error)
    {
        this.dm_error = dm_error;
    }

    public String getError_msg()
    {
        return error_msg;
    }

    public void setError_msg(String error_msg)
    {
        this.error_msg = error_msg;
    }

    public Integer getRecommend()
    {
        return recommend;
    }

    public void setRecommend(Integer recommend)
    {
        this.recommend = recommend;
    }

    public Integer getTotal_albums()
    {
        return total_albums;
    }

    public void setTotal_albums(Integer total_albums)
    {
        this.total_albums = total_albums;
    }

    public Integer getTotal_artists()
    {
        return total_artists;
    }

    public void setTotal_artists(Integer total_artists)
    {
        this.total_artists = total_artists;
    }

    public Integer getTotal_tracks()
    {
        return total_tracks;
    }

    public void setTotal_tracks(Integer total_tracks)
    {
        this.total_tracks = total_tracks;
    }

    public Integer getTrack_offset()
    {
        return track_offset;
    }

    public void setTrack_offset(Integer track_offset)
    {
        this.track_offset = track_offset;
    }

    public List<Tracks> getTracks()
    {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks)
    {
        this.tracks = tracks;
    }

}
