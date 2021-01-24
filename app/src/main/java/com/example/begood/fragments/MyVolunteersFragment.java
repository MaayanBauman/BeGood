package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.begood.R;

public class MyVolunteersFragment extends Fragment {
    public MyVolunteersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_voluteers, container, false);
        setHasOptionsMenu(true);

        Log.d("TAG","my volunteers page");

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("TAG","my volunteers menu");
        menu.clear();
        inflater.inflate(R.menu.my_volunteers_menu, menu);
    }
}
