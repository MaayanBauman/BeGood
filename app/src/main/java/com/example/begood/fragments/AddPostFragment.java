package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPostFragment extends Fragment {

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new, container, false);

        // Cancel post
        Button cancelBtn = view.findViewById(R.id.create_page_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        // Save new post
        Button saveBtn = view.findViewById(R.id.create_page_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save post on DB
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }
}