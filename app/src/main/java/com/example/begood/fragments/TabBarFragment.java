package com.example.begood.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.begood.R;

public class TabBarFragment extends Fragment implements View.OnClickListener {
    Fragment[] tabs = new Fragment[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_bar, container, false);
        Button posts = view.findViewById(R.id.tabfrag_posts_btn);
        Button add = view.findViewById(R.id.tabfrag_add_btn);

        posts.setTag(0);
        add.setTag(1);

        posts.setOnClickListener(this);
        add.setOnClickListener(this);

        tabs[0] = new FeedFragment();
        tabs[1] = new AddPostFragment();
        return view;
    }

    @Override
    public void onClick(View view) {
        int selected = (int)view.getTag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.tabfrag_container,tabs[selected]);
        tran.commit();
    }
}