package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.example.begood.models.Model;
import com.example.begood.models.Post;

import java.util.List;

public class FeedFragment extends Fragment {

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView rv = view.findViewById(R.id.feedfragm_list);

        rv.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        List<Post> data = Model.instance.getAllPosts();

        PostsAdapter adapter = new PostsAdapter(getLayoutInflater());
        adapter.data = data;
        rv.setAdapter(adapter);

        adapter.setOnClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","post was clicked " + position);
            }
        });

        return view;
    }

}