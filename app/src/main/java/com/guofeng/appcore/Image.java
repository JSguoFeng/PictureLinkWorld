package com.guofeng.appcore;

import android.net.Uri;

public class Image {
    private final String name;
    private final String path;
    private final Uri uri;
    private final int duration;
    private final int size;
    public Image(String name,String path, Uri uri,int duration,int size){
        this.name = name;
        this.path = path;
        this.uri = uri;
        this.duration = duration;
        this.size = size;
    }
    public String getName(){
        return this.name;
    }
    public Uri getUri(){
        return this.uri;
    }
    public int getDuration(){
        return this.duration;
    }
    public int getSize(){
        return this.size;
    }
    public String getPath(){
        return this.path;
    }
}
