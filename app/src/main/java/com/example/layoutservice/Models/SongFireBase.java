package com.example.layoutservice.Models;

import java.io.Serializable;

public class SongFireBase implements Serializable {
    private String title;
    private String singer;
    private String image;
    private String songUri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }


    public SongFireBase(String title, String singer, String image, String songUri) {
        this.title = title;
        this.singer = singer;
        this.image = image;
        this.songUri = songUri;
    }

    public String getImage() {
        return image;
    }

    public String getSongUri() {
        return songUri;
    }

    public SongFireBase() {}
}
