package com.example.begood.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.begood.models.Model;
import com.example.begood.models.Post;

import java.util.List;

public class PostsListViewModel extends ViewModel {
    private LiveData<List<Post>> postList;

    public PostsListViewModel() {
        postList = Model.instance.getAllPosts();
    }
    public LiveData<List<Post>> getPostList(){
        return postList;
    }
}
