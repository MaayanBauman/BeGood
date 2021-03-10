package com.example.begood.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import com.example.begood.MyApplication;

import java.util.List;

public class Model {
    public final static Model instance = new Model();

    ModelFireBase modelFirebase = new ModelFireBase();
    ModelSQL modelSql = new ModelSQL();

    private Model() { }

    public interface Listener<T> {
        void onComplete(T result);
    }

    LiveData<List<Post>> postList;
    public interface GetAllPostsListener extends Listener<List<Post>> { }
    public LiveData<List<Post>> getAllPosts() {
        // modelFirebase.getAllPosts(listener);

        if (postList == null) {
            postList = modelSql.getAllPosts();
            refreshAllPosts(null);
        }

        return postList;
    }

    public interface GetAllStudentsListener{
        void onComplete();
    }
    public void refreshAllPosts(final GetAllStudentsListener listener) {
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);

        modelFirebase.getAllPosts(lastUpdated, new GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> result) {
                long lastU = 0;

                for (Post post: result) {
                    modelSql.AddPost(post,null);

                    if (post.getLastUpdated() > lastU) {
                        lastU = post.getLastUpdated();
                    }
                }

                sp.edit().putLong("lastUpdated", lastU).commit();

                if (listener != null){
                    listener.onComplete();
                }
            }
        });
    }

    public interface AddPostListener { void onComplete(); }
    public void AddPost(Post post, final AddPostListener listener) {
        modelFirebase.addPost(post, () -> {
            refreshAllPosts(() -> {
                listener.onComplete();
            });
        });
    }

    public interface DeleteListener { void onComplete(); }
    public void deletePost(String postId, final DeleteListener listener){
        modelFirebase.delete(postId, listener);
    }

    public interface UploadImageListener extends Listener<String> { }
    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }

    public interface AddUserListener { void onComplete(); }
    public void AddUser(User user, final AddUserListener listener) {
        modelFirebase.addUser(user, listener);
    }

    public interface UpdateUserListener { void onComplete(); }
    public void UpdateUser(User user, final UpdateUserListener listener) {
        modelFirebase.updateUser(user, listener);
    }

    public interface GetUserByIdListener extends Listener<User> { }
    public void GetUserById(String userId, final GetUserByIdListener listener) {
        modelFirebase.getUserById(userId, listener);
    }

    public interface GetUserRegisteredPostsListener extends Listener<List<String>> { }
    public void GetUserRegisteredPosts(String userId, final GetUserRegisteredPostsListener listener) {
        modelFirebase.getUserRegisteredPosts(userId, listener);
    }
}
