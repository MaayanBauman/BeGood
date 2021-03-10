package com.example.begood.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "posts")
public class Post implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private String date;
    private String location;
    private String type;
    private String spacialNeeds;
    private String authorId;
    private String image;
    private Long lastUpdated;

    public Post() { this.setId("id" + Math.random()); }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
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

    public void setDate(String date) {
        this.date = date;
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

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("title", title);
        result.put("description", description);
        result.put("date", date);
        result.put("location", location);
        result.put("type", type);
        result.put("spacialNeeds", spacialNeeds);
        result.put("authorId", authorId);
        result.put("image", image);
        result.put("lastUpdated", FieldValue.serverTimestamp());

        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (String)map.get("id");
        title = (String)map.get("title");
        description = (String)map.get("description");
        date = (String)map.get("date");
        location = (String)map.get("location");
        type = (String)map.get("type");
        spacialNeeds = (String)map.get("spacialNeeds");
        authorId = (String)map.get("authorId");
        image = (String)map.get("image");
        Timestamp timestamp = (Timestamp)map.get("lastUpdated");
        lastUpdated = timestamp.getSeconds();
    }
}
