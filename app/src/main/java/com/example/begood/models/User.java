package com.example.begood.models;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class User implements Serializable {

    @PrimaryKey
    @NonNull
    private String _email;
    private String _fullname;
    private String _image_url;

    public User(String email, String fullname, Uri image_url){
        this.setEmail(email);
        this.setFullname(fullname);
        this.setImageURL(image_url);
    }

    // getters
    public String getEmail(){
        return this._email;
    }

    public String getFullname(){
        return this._fullname;
    }

    public String getImageURL(){
        return this._image_url;
    }

    //setters

    public void setEmail(String email){
        this._email = email;
    }

    public void setFullname(String fullname){
        this._fullname = fullname;
    }

    public void setImageURL(Uri image_url){
        this._image_url = image_url.toString();
    }
}