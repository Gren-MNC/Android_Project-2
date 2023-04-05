package com.example.layoutservice.Models;

public class Singer {
    private int idSinger;
    private String name;
    private String path;

    public Singer(int idSinger, String name, String path) {
        this.idSinger = idSinger;
        this.name = name;

        this.path = path;
    }
    public Singer(){}

    public int getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(int idSinger) {
        this.idSinger = idSinger;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
