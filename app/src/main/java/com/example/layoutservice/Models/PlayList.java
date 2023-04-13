package com.example.layoutservice.Models;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlayList implements Serializable {

private String ten;
private Uri hinhNen;
private List<SongFireBase> songFireBaseList;

    public PlayList(String ten, Uri hinhNen, List<SongFireBase> songFireBaseList) {
        this.ten = ten;
        this.hinhNen = hinhNen;
        this.songFireBaseList = songFireBaseList;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Uri getHinhNen() {
        return hinhNen;
    }

    public void setHinhNen(Uri hinhNen) {
        this.hinhNen = hinhNen;
    }

    public List<SongFireBase> getSongFireBaseList() {
        return songFireBaseList;
    }

    public void setSongFireBaseList(List<SongFireBase> songFireBaseList) {
        this.songFireBaseList = songFireBaseList;
    }
}