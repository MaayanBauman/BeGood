package com.example.begood.adapters;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

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
    Button subscribe;
    String userId;
    User currUser;
    boolean isSubscribed;

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
        subscribe = itemView.findViewById(R.id.post_subscribe_btn);
        userId = LoginFragment.getAccount().getId();
    }

    public void bindData(Post post, int position) {
        if (post.getImage() != null) {
            Picasso.get().load(post.getImage()).placeholder(R.drawable.avatar).into(image);
        }
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        time.setText(post.getTime());
        location.setText(post.getLocation());
        spacialNeeds.setText(post.getSpacialNeeds());
        type.setText(post.getType());
        this.position = position;
        Model.instance.GetUserById(post.getAuthor(), user -> {
            if(user != null){
                author.setText(user.getFullName());
            } else {
                Log.d("Error", "couldn't find user with ID:" + userId);
            }
        });
        Model.instance.GetUserById(userId, user -> {
            if(user != null){
                currUser = user;
                isSubscribed = currUser.getRegisteredPosts().contains(post);
                if (isSubscribed) {
                    subscribe.setText("unsubscribe");
                } else {
                    subscribe.setText("subscribe");
                }

                subscribe.setOnClickListener(new View.OnClickListener() {
                    List<Post> registeredPosts = new ArrayList<>();

                    @Override
                    public void onClick(View view) {
                        if (isSubscribed) {
                            int postIndex = registeredPosts.indexOf(post);
                            registeredPosts.remove(postIndex);
                        } else {
                            registeredPosts.add(post);
                        }
                        currUser.setRegisteredPosts(registeredPosts);
                        Model.instance.UpdateUser(currUser, () -> {
                            if (isSubscribed) {
                                subscribe.setText("subscribe");
                                isSubscribed = false;
                            } else {
                                subscribe.setText("unsubscribe");
                                isSubscribed = true;
                            }
                        });
                    }
                });
            } else {
                Log.d("Error", "couldn't find user with ID:" + userId);
            }
        });
    }
}
