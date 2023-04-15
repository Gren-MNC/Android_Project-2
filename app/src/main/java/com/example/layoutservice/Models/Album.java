package com.example.layoutservice.Models;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

private String tenAlbum;
private String tenCaSiAlbum;
private String hinhAlbum;
private ArrayList<SongFireBase> AlbumList;

    public Album(String tenAlbum, String tenCaSiAlbum, String hinhAlbum, ArrayList<SongFireBase> albumList) {
        this.tenAlbum = tenAlbum;
        this.tenCaSiAlbum = tenCaSiAlbum;
        this.hinhAlbum = hinhAlbum;
        AlbumList = albumList;
    }

    public Album() {}

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }

    public String getTenCaSiAlbum() {
        return tenCaSiAlbum;
    }

    public void setTenCaSiAlbum(String tenCaSiAlbum) {
        this.tenCaSiAlbum = tenCaSiAlbum;
    }

    public String getHinhAlbum() {
        return hinhAlbum;
    }

    public void setHinhAlbum(String hinhAlbum) {
        this.hinhAlbum = hinhAlbum;
    }

    public ArrayList<SongFireBase> getAlbumList() {
        return AlbumList;
    }

    public void setAlbumList(ArrayList<SongFireBase> albumList) {
        AlbumList = albumList;
    }
}