package com.example.a10012032.dreamapplicationv2.Main;



public class Post {

    private int likes;
    private String caption, username, bitmapString;
    public Post(){ }
    public Post(int likes, String bitmapString, String caption, String username){
        this.likes=likes;
        this.bitmapString=bitmapString;
        this.caption=caption;
        this.username=username;
    }

    public int getLikes(){
        return likes;
    }

    public String getBitmapString(){
        return bitmapString;
    }

    public String getCaption(){
        return caption;
    }

    public String getUsername(){
        return username;
    }
}
