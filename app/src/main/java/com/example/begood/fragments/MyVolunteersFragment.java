package com.example.begood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.example.begood.viewModels.PostsListViewModel;

import java.util.LinkedList;
import java.util.List;

public class MyVolunteersFragment extends Fragment {
    List<Post> registeredPosts = new LinkedList<>();
    PostsListViewModel postList;
    ProgressBar pb;
    PostsAdapter adapter;

    public MyVolunteersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_voluteers, container, false);
        setHasOptionsMenu(true);

        postList = new ViewModelProvider(this).get(PostsListViewModel.class);

        RecyclerView rv = view.findViewById(R.id.my_volunteers_fragm_list);

        pb = view.findViewById(R.id.my_volunteers_progress_bar);
        pb.setVisibility(View.INVISIBLE);

        adapter = new PostsAdapter(getLayoutInflater(), this.getUserRegisteredPosts());

        rv.setAdapter(adapter);

        // Create postsList with rv
        rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        postList.getPostList().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                adapter.data = getUserRegisteredPosts();
                adapter.notifyDataSetChanged();
            }
        });

        reloadData();

        return view;
    }

    private List<Post> getUserRegisteredPosts() {
        String userId = LoginFragment.getAccount().getId();

        Model.instance.GetUserRegisteredPosts(userId, data -> {
            Post currPost;

            for (String postId: data) {
                currPost = this.getPostById(postId, this.postList.getPostList().getValue());

                if ((currPost != null)  && !currPost.getIsDeleted()) {
                    if (this.getPostById(currPost.getId(), this.registeredPosts) == null) {
                        this.registeredPosts.add(currPost);
                    }
                } else {
                    this.registeredPosts.remove(currPost);
                }
            }
        });

        return this.registeredPosts;
    }

    private Post getPostById(String postId, List<Post> list) {
        for (Post post : list) {
            if (post.getId().equals(postId)) {
                return post;
            }
        }

        return null;
    }

    private void reloadData() {
        pb.setVisibility(View.VISIBLE);

        Model.instance.refreshAllPosts(new Model.GetAllPostListener() {
            @Override
            public void onComplete() {
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.my_volunteers_menu, menu);
    }
}
