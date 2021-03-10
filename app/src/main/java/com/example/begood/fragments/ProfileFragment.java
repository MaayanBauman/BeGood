package com.example.begood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {
    // List<Post> posts = new LinkedList<>();
    PostsListViewModel postList;
    ProgressBar pb;
    PostsAdapter adapter;
    ImageView userPhoto;
    TextView userName;
    TextView userMail;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        postList = new ViewModelProvider(this).get(PostsListViewModel.class);

        userPhoto = view.findViewById(R.id.profile_user_photo);
        userName = view.findViewById(R.id.profile_user_name);
        userMail =  view.findViewById(R.id.profile_user_mail);
        userName.setText(LoginFragment.getAccount().getDisplayName());
        userMail.setText(LoginFragment.getAccount().getEmail());
        Picasso.get().load(LoginFragment.getAccount().getPhotoUrl()).placeholder(R.drawable.avatar).into(userPhoto);

        RecyclerView rv = view.findViewById(R.id.profile_fragm_list);
        pb = view.findViewById(R.id.profile_progress_bar);
        pb.setVisibility(View.INVISIBLE);
        adapter = new PostsAdapter(getLayoutInflater(), this.getUserUploadPosts());
        rv.setAdapter(adapter);

        // Create postsList with rv
        rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        postList.getPostList().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> students) {
                adapter.notifyDataSetChanged();
            }
        });

        reloadData();
        return view;
    }

    private List<Post> getUserUploadPosts() {
        String userId = LoginFragment.getAccount().getId();
        List<Post> uploadPosts = new LinkedList<>();

        for (Post post: this.postList.getPostList().getValue()) {
            if (post.getAuthorId().compareTo(userId) == 0) {
                uploadPosts.add(post);
            }
        }

        return uploadPosts;
    }

    void reloadData(){
        pb.setVisibility(View.VISIBLE);
        String userId = LoginFragment.getAccount().getId();

        Model.instance.refreshAllPosts(new Model.GetAllStudentsListener() {
            @Override
            public void onComplete() {
                // posts = data;
                pb.setVisibility(View.INVISIBLE);
                getUserUploadPosts();
                // swipeRefresh.setRefreshing(false);
                // adapter.data = postList.getPostList();
                // adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.profile_menu, menu);
    }
}