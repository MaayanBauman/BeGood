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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.example.begood.models.Model;
import com.example.begood.models.Post;

import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {
    List<Post> posts = new LinkedList<>();
    ProgressBar pb;
    PostsAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        RecyclerView rv = view.findViewById(R.id.profile_list);

        pb = view.findViewById(R.id.profile_progress_bar);
        pb.setVisibility(View.INVISIBLE);
        adapter = new PostsAdapter(getLayoutInflater());

        rv.setAdapter(adapter);

        // Create postsList with rv
        rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        reloadData();

        return view;
    }

    void reloadData(){
        pb.setVisibility(View.VISIBLE);
        String userId = LoginFragment.getAccount().getId();
        Model.instance.GetUserUploadPosts(userId, data -> {
            posts = data;
            pb.setVisibility(View.INVISIBLE);
            adapter.data = posts;
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.profile_menu, menu);
    }
}