package com.example.layoutservice.Models;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {

private String tenAlbum;
private String tenCaSiAlbum;
private Uri hinhAlbum;
private List<SongFireBase> AlbumList;

    public Album(String tenAlbum, String tenCaSiAlbum, Uri hinhAlbum, List<SongFireBase> albumList) {
        this.tenAlbum = tenAlbum;
        this.tenCaSiAlbum = tenCaSiAlbum;
        this.hinhAlbum = hinhAlbum;
        AlbumList = albumList;
    }

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

    public Uri getHinhAlbum() {
        return hinhAlbum;
    }

    public void setHinhAlbum(Uri hinhAlbum) {
        this.hinhAlbum = hinhAlbum;
    }

    public List<SongFireBase> getAlbumList() {
        return AlbumList;
    }

    public void setAlbumList(List<SongFireBase> albumList) {
        AlbumList = albumList;
    }
}