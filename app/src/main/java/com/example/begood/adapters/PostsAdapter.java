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
    public List<Post> data = new LinkedList<Post>();
    LayoutInflater inflater;

    public PostsAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.volunteer_post, parent,false);
        PostViewHolder holder = new PostViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bindData(post, position);
    }

    @Override
    public int getItemCount() {
        if (data != null ){
            return data.size();
        }
        return 0;
    }
}
