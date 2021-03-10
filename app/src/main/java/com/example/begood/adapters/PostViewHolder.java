package com.example.begood.adapters;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.begood.R;
import com.example.begood.fragments.FeedFragmentDirections;
import com.example.begood.fragments.LoginFragment;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.example.begood.models.User;
import com.squareup.picasso.Picasso;

public class PostViewHolder extends RecyclerView.ViewHolder {
    public PostsAdapter.onDeletePostClick onDeletePost;
    int position;
    boolean isSubscribed;

    RelativeLayout postFragment;
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
    Post currPost;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.postFragment = itemView.findViewById((R.id.post_fragment));
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
        currPost = post;
        this.title.setText(currPost.getTitle());
        this.description.setText(currPost.getDescription());
        this.time.setText(currPost.getDate());
        this.location.setText(currPost.getLocation());
        this.spacialNeeds.setText(currPost.getSpacialNeeds());
        this.type.setText(currPost.getType());
        this.position = position;
        this.setPhoto();
        this.setSubscribe();
        this.setAuthor();
    }

    private void setPhoto() {
        if (currPost.getImage() != null) {
            Picasso.get().load(currPost.getImage()).placeholder(R.drawable.avatar).into(this.image);
        }
    }

    private void setSubscribe() {
        Model.instance.GetUserById(userId, user -> {
            if (user != null) {
                this.currUser = user;
                this.isSubscribed = this.currUser.getPostIndexOnRegisteredPosts(currPost) != -1;

                if (this.isSubscribed) {
                    this.subscribe.setText("unsubscribe");
                } else {
                    this.subscribe.setText("subscribe");
                }

                this.setSubscribeOnClick();
            } else {
                Log.d("Error", "couldn't find user with ID:" + userId);
            }
        });
    }

    private void setSubscribeOnClick() {
        this.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribe.setEnabled(false);
                if (isSubscribed) {
                    currUser.removeRegisteredPost(currPost);
                    subscribe.setText("subscribe");
                    isSubscribed = false;
                } else {
                    currUser.addRegisteredPost(currPost);
                    subscribe.setText("unsubscribe");
                    isSubscribed = true;
                }

                Model.instance.UpdateUser(currUser, () -> {
                    subscribe.setEnabled(true);
                });
            }
        });
    }

    private void setAuthor() {
        Model.instance.GetUserById(currPost.getAuthorId(), user -> {
            if (user != null) {
                this.author.setText(user.getFullName());
                this.displayActions(user.getId());
            } else {
                Log.d("Error", "couldn't find user with ID:" + currPost.getAuthorId());
            }
        });
    }

    private void displayActions(String authorId) {
        if (currPost.getAuthorId().compareTo(userId) == 0) {
            this.setEditOnClick();
            this.setDeleteOnClick();
            this.editPost.setVisibility(View.VISIBLE);
            this.deletePost.setVisibility(View.VISIBLE);
            this.subscribe.setVisibility(View.GONE);
        } else {
            this.deletePost.setVisibility(View.GONE);
            this.editPost.setVisibility(View.GONE);
            this.subscribe.setVisibility(View.VISIBLE);
        }
    }

    private void setDeleteOnClick() {
        this.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.deletePost(currPost, () -> {
                    onDeletePost.deleteItem(currPost);
                });
            }
        });
    }

    private void setEditOnClick() {
        this.editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(FeedFragmentDirections.actionFeedFrgToAddPostFrg(currPost));
            }
        });
    }
}
