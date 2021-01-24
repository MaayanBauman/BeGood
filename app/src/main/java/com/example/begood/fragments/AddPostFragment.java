package com.example.begood.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.begood.R;
import com.example.begood.models.Model;
import com.example.begood.models.Post;

public class AddPostFragment extends Fragment {

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new, container, false);

        setHasOptionsMenu(true);

        ProgressBar pb = view.findViewById(R.id.create_page_progress_bar);
        pb.setVisibility(View.INVISIBLE);

        // Cancel post
        Button cancelBtn = view.findViewById(R.id.create_page_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        // Save new post
        Button saveBtn = view.findViewById(R.id.create_page_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                cancelBtn.setEnabled(false);
                saveBtn.setEnabled(false);

//                TextInputEditText titleTV = view.findViewById(R.id.create_page_filed_title_input);
//                TextInputEditText descriptionTV = view.findViewById(R.id.create_page_filed_description_input);
                EditText timeTV = view.findViewById(R.id.create_page_filed_date_input);
                TextView typeTV = view.findViewById(R.id.create_page_filed_type_text);
//                TextInputEditText locationTV = view.findViewById(R.id.create_page_filed_location_input);

//                String title = titleTV.getText().toString();
//                String description = descriptionTV.getText().toString();
//                String time = timeTV.getText().toString();
//                String type = typeTV.getText().toString();
//                String location = locationTV.getText().toString();
                String spacialNeeds = "";
                String author = "me";
                String title = "title";
                String description = "description";
                String location = "location";
                String time = "time";
                String type = "type";
                Post newPost = new Post(title, description, time, type, location, spacialNeeds, author);
                Model.instance.AddPost(newPost, new Model.AddPostListener() {
                    @Override
                    public void onComplete() {
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        Log.d("TAG","add post menu");
        menu.clear();
        inflater.inflate(R.menu.add_post_menu, menu);
    }
}