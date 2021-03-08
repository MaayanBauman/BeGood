package com.example.begood.adapters;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.fragments.LoginFragment;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.example.begood.models.User;
import com.squareup.picasso.Picasso;

public class PostViewHolder extends RecyclerView.ViewHolder {
    int position;
    boolean isSubscribed;

    ImageView image;
    TextView title;
    TextView description;
    TextView time;
    TextView location;
    TextView spacialNeeds;
    TextView type;
    TextView author;
    Button subscribe;
    ImageButton deletePost;
    ImageButton editPost;
    String userId;
    User currUser;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = itemView.findViewById(R.id.post_image);
        this.title = itemView.findViewById(R.id.post_title);
        this.description = itemView.findViewById(R.id.post_description);
        this.time = itemView.findViewById(R.id.post_time_value);
        this. location = itemView.findViewById(R.id.post_location_value);
        this.spacialNeeds = itemView.findViewById(R.id.post_needs_value);
        this.type = itemView.findViewById(R.id.post_type_value);
        this.author = itemView.findViewById(R.id.post_author_value);
        this.subscribe = itemView.findViewById(R.id.post_subscribe_btn);
        this.deletePost = itemView.findViewById(R.id.delete_post_btn);
        this.editPost = itemView.findViewById(R.id.edit_post_btn);
        this.userId = LoginFragment.getAccount().getId();
    }

    public void bindData(Post post, int position) {
        this.title.setText(post.getTitle());
        this.description.setText(post.getDescription());
        this.time.setText(post.getTime());
        this.location.setText(post.getLocation());
        this.spacialNeeds.setText(post.getSpacialNeeds());
        this.type.setText(post.getType());
        this.position = position;
        this.setPhoto(post.getImage());
        this.setSubscribe(post);
        this.setAuthor(post.getAuthorId());
    }

    private void setPhoto(String image) {
        if (image != null) {
            Picasso.get().load(image).placeholder(R.drawable.avatar).into(this.image);
        }
    }

    private void setSubscribe(Post post) {
        Model.instance.GetUserById(userId, user -> {
            if (user != null) {
                this.currUser = user;
                this.isSubscribed = this.currUser.getPostIndexOnRegisteredPosts(post) != -1;

                if (this.isSubscribed) {
                    this.subscribe.setText("unsubscribe");
                } else {
                    this.subscribe.setText("subscribe");
                }

                this.setSubscribeOnClick(post);
            } else {
                Log.d("Error", "couldn't find user with ID:" + userId);
            }
        });
    }

    private void setSubscribeOnClick(Post post) {
        this.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribe.setEnabled(false);

                if (isSubscribed) {
                    currUser.removeRegisteredPost(post);
                    subscribe.setText("subscribe");
                    isSubscribed = false;
                } else {
                    currUser.addRegisteredPost(post);
                    subscribe.setText("unsubscribe");
                    isSubscribed = true;
                }

                Model.instance.UpdateUser(currUser, () -> {
                    subscribe.setEnabled(true);
                });
            }
        });
    }

    private void setAuthor(String authorId) {
        Model.instance.GetUserById(authorId, user -> {
            if (user != null) {
                this.author.setText(user.getFullName());
                this.displayActions(user.getId());
            } else {
                Log.d("Error", "couldn't find user with ID:" + authorId);
            }
        });
    }

    private void displayActions(String authorId) {
        if (authorId.compareTo(userId) == 0) {
            this.deletePost.setVisibility(View.VISIBLE);
            this.editPost.setVisibility(View.VISIBLE);
        } else {
            this.deletePost.setVisibility(View.GONE);
            this.editPost.setVisibility(View.GONE);
        }
    }

    private void setDeleteOnClick(Post post) {
        this.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // Model.instance.deletePost(post.getId(), null);
            }
        });
    }

    private void setEditOnClick(Post post) {
        this.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // TODO
            }
        });
    }
}
