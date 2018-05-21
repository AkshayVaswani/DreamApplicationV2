package com.example.a10012032.dreamapplicationv2.Main;



public class Post {


    private String caption, username, bitmapString;
    public Post(){ }
    public Post( String bitmapString, String caption, String username){

        this.bitmapString=bitmapString;
        this.caption=caption;
        this.username=username;
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
