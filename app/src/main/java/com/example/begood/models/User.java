package com.example.begood.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "users")
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    private String _id;
    private String _email;
    private String _fullName;
    private String _image_url;
    @TypeConverters(PostArrayConverter.class)
    private List<Post> _registeredPosts;
    public User(){
        this.setId("id" + Math.random());
    }

    public User(String id, String email, String fullName, String image_url){
        this.setId(id);
        this.setEmail(email);
        this.setFullName(fullName);
        this.set_image_url(image_url);
        ArrayList<Post> regPosts = new ArrayList<>();
        this.setRegisteredPosts(regPosts);
    }

    // getters
    public String getId(){
        return this._id;
    }
    public String getEmail(){
        return this._email;
    }
    public String getFullName(){
        return this._fullName;
    }
    public String get_image_url() {return _image_url;}


    public List<Post> getRegisteredPosts(){
        return this._registeredPosts;
    }

    //setters
    public void setId(String id){
        this._id = id;
    }
    public void setEmail(String email){
        this._email = email;
    }
    public void setFullName(String fullName){
        this._fullName = fullName;
    }
    public void set_image_url(String image_url){
        this._image_url = image_url;
    }
    public void setRegisteredPosts(List<Post> registeredPosts) { this._registeredPosts = registeredPosts; }
}