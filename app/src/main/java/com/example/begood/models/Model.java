package com.example.begood.models;

import android.graphics.Bitmap;

import java.util.List;

public class Model {
    public final static Model instance = new Model();

    ModelFireBase modelFirebase = new ModelFireBase();
    ModelSQL modelSql = new ModelSQL();

    private Model() {
    }

    public interface Listener<T>{
        void onComplete(T result);
    }

    public interface GetAllPostsListener extends Listener<List<Post>>{}
    public void getAllPosts(final GetAllPostsListener listener) {
        modelFirebase.getAllPosts(listener);
    }
    public interface GetPostByIdListener extends Listener<Post>{}
    public void getPostById(String id, final GetPostByIdListener listener) {
        modelFirebase.getPostById(id, listener);
    }

    public interface AddPostListener {
        void onComplete();
    }
    public void AddPost(Post post, final AddPostListener listener) {
        modelFirebase.addPost(post, listener);
    }

    interface  DeleteListener extends AddPostListener{}
    public void deletePost(String postId, DeleteListener listener){
        modelFirebase.delete(postId, listener);
    }
    public interface UploadImageListener extends Listener<String>{ }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }

}
