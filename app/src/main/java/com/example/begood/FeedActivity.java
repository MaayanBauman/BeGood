package com.example.begood;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.fragments.TabBarFragment;

public class FeedActivity extends AppCompatActivity{
    RecyclerView postsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Log.d("Tag", "feed");
        final TabBarFragment tabBar = new TabBarFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.feed_frg_container,tabBar);
        tran.commit();
    }
}
