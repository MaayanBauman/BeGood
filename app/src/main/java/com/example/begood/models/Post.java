package com.example.begood.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "posts")
public class Post implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private String time;
    private String location;
    private String type;
    private String spacialNeeds;
    private String authorId;
    private String image;

    public Post() {this.setId("id" + Math.random());}

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

    public String getAuthorId() {
        return this.authorId;
    }

    public String getImage() { return this.image; }

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

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setImage(String imageUrl) { this.image = imageUrl; }
}
