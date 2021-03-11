package com.example.begood.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.models.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder> {
    public List<Post> data = new LinkedList<>();
    LayoutInflater inflater;

    public PostsAdapter(LayoutInflater inflater, List<Post> postList) {
        this.inflater = inflater;
        this.data = postList;
    }

    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.volunteer_post, parent,false);
        PostViewHolder holder = new PostViewHolder(view);
        holder.onDeletePost = this.deletePost;
        return holder;
    }

    public interface onDeletePostClick {
        void deleteItem(Post post);
    }
    private onDeletePostClick deletePost = new onDeletePostClick() {
        @Override
        public void deleteItem(Post post) {
            data.remove(post);
            notifyDataSetChanged();
        }
    };

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bindData(post, position);
    }

    @Override
    public int getItemCount() {
        if (data != null){
            return data.size();
        }
        return 0;
    }
}
