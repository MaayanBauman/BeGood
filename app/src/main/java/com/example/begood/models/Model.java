package com.example.begood.models;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();

    private Model(){
        for(int i=0;i<10;i++) {
            Post post = new Post();
            post.id = "" + i;
//            post.image;
            post.title = "Post Title #" + i;
            post.description = "Description #" + i;
            post.time = "Time #" + i;
            post.location = "Location #" + i;
            post.spacialNeeds = "Spacial Needs #" + i;
            post.type = "Type #" + i;
            post.author = "Author #" + i;
            data.add(post);
        }
    }

    List<Post> data = new LinkedList<Post>();

    public List<Post> getAllPosts() {
        return data;
    }
}
