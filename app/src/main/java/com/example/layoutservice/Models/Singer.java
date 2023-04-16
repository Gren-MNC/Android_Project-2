package com.example.layoutservice.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Singer implements Serializable {
    private String nameSinger;
    private String imageSinger;
    private ArrayList<SongFireBase> ListSong;
    public Singer(String nameSinger, String imageSinger, ArrayList<SongFireBase> listSong) {
        this.nameSinger = nameSinger;
        this.imageSinger = imageSinger;
        ListSong = listSong;
    }
    public Singer(){}

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getImageSinger() {
        return imageSinger;
    }

    public void setImageSinger(String imageSinger) {
        this.imageSinger = imageSinger;
    }

    public ArrayList<SongFireBase> getListSong() {
        return ListSong;
    }

    public void setListSong(ArrayList<SongFireBase> listSong) {
        ListSong = listSong;
    }
}
