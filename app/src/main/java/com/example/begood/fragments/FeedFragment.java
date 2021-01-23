package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class FeedFragment extends Fragment {
    List<Post> posts = new LinkedList<Post>();
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Create postsList with rv
        RecyclerView rv = view.findViewById(R.id.feedfragm_list);
        rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        Model.instance.getAllPosts(new Model.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> data) {
                posts = data;
            }
        });

        PostsAdapter adapter = new PostsAdapter(getLayoutInflater());
        adapter.data = posts;
        rv.setAdapter(adapter);

        adapter.setOnClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","post was clicked " + position);
                String postId = "" + position;
                FeedFragmentDirections.ActionFeedFrgToPostInfoFrg action = FeedFragmentDirections.actionFeedFrgToPostInfoFrg(postId);
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Navigate to create new post fragment
        FloatingActionButton addNewBtn = view.findViewById(R.id.feed_add_post_btn);
        addNewBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_feedFrg_to_addPostFrg));

        return view;
    }

}