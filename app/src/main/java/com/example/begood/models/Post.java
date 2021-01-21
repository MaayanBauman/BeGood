package com.example.begood.models;

public class Post {
    public String id;
    //image;
    public String title;
    public String description;
    public String time;
    public String location;
    public String type;
    public String spacialNeeds;
    public String author;

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
}
