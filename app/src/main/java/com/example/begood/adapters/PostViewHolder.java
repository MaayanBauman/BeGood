package com.example.begood.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    public PostsAdapter.OnItemClickListener listener;
    int position;

    ImageView image;
    TextView title;
    TextView description;
    EditText time;
    TextView location;
    TextView spacialNeeds;
    TextView type;
    TextView author;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.post_image);
        title = itemView.findViewById(R.id.post_title);
        description = itemView.findViewById(R.id.post_description);
        time = itemView.findViewById(R.id.post_time_value);
        location = itemView.findViewById(R.id.post_location_value);
        spacialNeeds = itemView.findViewById(R.id.post_needs_value);
        type = itemView.findViewById(R.id.post_type_value);
        author = itemView.findViewById(R.id.post_author_value);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Post post, int position) {
//        image.set(post.image);
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        time.setText(post.getTime());
        location.setText(post.getLocation());
        spacialNeeds.setText(post.getSpacialNeeds());
        type.setText(post.getType());
        author.setText(post.getAuthor());
        this.position = position;
    }
}
