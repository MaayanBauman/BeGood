package com.example.begood.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String id;
    //image;
    private String title;
    private String description;
    private String time;
    private String location;
    private String type;
    private String spacialNeeds;
    private String author;

    public Post(){

    }

    public Post(String title, String description, String time, String location, String type, String spacialNeeds, String author){
        this.title = title;
        this.description = description;
        this.time = time;
        this.location = location;
        this.type = type;
        this.spacialNeeds = spacialNeeds;
        this.author = author;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTime() {
        return this.time;
    }

    public String getType() {
        return this.type;
    }

    public String getSpacialNeeds() {
        return this.spacialNeeds;
    }

    public String getLocation() {
        return this.location;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title= title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpacialNeeds(String spacialNeeds) {
        this.spacialNeeds = spacialNeeds;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
