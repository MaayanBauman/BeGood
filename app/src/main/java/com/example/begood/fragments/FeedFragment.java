package com.example.begood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.begood.MainActivity;
import com.example.begood.R;
import com.example.begood.adapters.PostsAdapter;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.example.begood.viewModels.PostsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class FeedFragment extends Fragment {
    // List<Post> posts = new LinkedList<>();
    PostsListViewModel postList;

    ProgressBar pb;
    FloatingActionButton addNewBtn;
    PostsAdapter adapter;
    // SwipeRefreshLayout swipeRefresh;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).getSupportActionBar().show();

        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        postList = new ViewModelProvider(this).get(PostsListViewModel.class);

        TextView greetingMessage = view.findViewById(R.id.greeting_message);
        String greetingText = "שלום " + LoginFragment.getAccount().getDisplayName();
        greetingMessage.setText(greetingText);
        
        RecyclerView rv = view.findViewById(R.id.feedfragm_list);
        pb = view.findViewById(R.id.feed_progress_bar);
        addNewBtn = view.findViewById(R.id.feed_add_post_btn);

        pb.setVisibility(View.INVISIBLE);

        adapter = new PostsAdapter(getLayoutInflater(), this.getNotDeletedPosts());
        rv.setAdapter(adapter);

        // Create postsList with rv
        rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        postList.getPostList().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                adapter.data = getNotDeletedPosts();
                adapter.notifyDataSetChanged();
            }
        });

        reloadData();

        // Navigate to create new post fragment
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post newPost = new Post();
                newPost.setAuthorId(LoginFragment.getAccount().getId());
                Navigation.findNavController(view).navigate(FeedFragmentDirections.actionFeedFrgToAddPostFrg(newPost));
            }
        });

        return view;
    }

    private List<Post> getNotDeletedPosts() {
        List<Post> notDeletedPosts = new LinkedList<>();

        if (this.postList.getPostList().getValue() != null) {
            for (Post post : this.postList.getPostList().getValue()) {
                if ((post != null) && !post.getIsDeleted()) {
                    notDeletedPosts.add(post);
                }
            }
        }

        return notDeletedPosts;
    }

    private void reloadData(){
        pb.setVisibility(View.VISIBLE);
        addNewBtn.setEnabled(false);

        Model.instance.refreshAllPosts(new Model.GetAllPostListener() {
            @Override
            public void onComplete() {
                // posts = data;
                pb.setVisibility(View.INVISIBLE);
                addNewBtn.setEnabled(true);
                adapter.data = getNotDeletedPosts();
                // swipeRefresh.setRefreshing(false);
                // adapter.data = postList.getPostList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }
}