package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.begood.R;

public class PostInfoFragment extends Fragment {

    public PostInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_info, container, false);
        String postId = PostInfoFragmentArgs.fromBundle(getArguments()).getPostId();
        Log.d("TAG","post id is: " + postId);

        return view;
    }
}