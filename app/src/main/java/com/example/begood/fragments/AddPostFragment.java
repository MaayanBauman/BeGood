package com.example.begood.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.begood.R;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.example.begood.models.User;
import com.google.android.material.textfield.TextInputEditText;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddPostFragment extends Fragment {
    // Fragment members
    TextInputEditText titleTIEV;
    TextInputEditText descriptionTIEV;
    TextInputEditText specialNeedsTIEV;
    TextInputEditText typeTIEV;
    Button imageBtn;
    ImageView imageIV;
    EditText timeEV;
    TextInputEditText locationTIEV;
    ProgressBar pb;
    Button cancelBtn;
    Button saveBtn;
    View view;
    User currUser;

    public AddPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_new, container, false);
        currUser = AddPostFragmentArgs.fromBundle(getArguments()).getUser();
        setHasOptionsMenu(true);

        // Fragment members
        titleTIEV = view.findViewById(R.id.create_page_filed_title_input);
        descriptionTIEV = view.findViewById(R.id.create_page_filed_description_input);
        imageBtn = view.findViewById(R.id.create_page_filed_image_btn);
        imageIV = view.findViewById(R.id.create_page_filed_image);
        timeEV = view.findViewById(R.id.create_page_filed_date_input);
        typeTIEV = view.findViewById(R.id.create_page_filed_type_input);
        locationTIEV = view.findViewById(R.id.create_page_filed_location_input);
        specialNeedsTIEV = view.findViewById(R.id.create_page_filed_needs_input);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        pb = view.findViewById(R.id.create_page_progress_bar);
        pb.setVisibility(View.INVISIBLE);

        // Cancel post
        cancelBtn = view.findViewById(R.id.create_page_cancel_btn);

        cancelBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_global_feedFrg));

        // Save new post
        saveBtn = view.findViewById(R.id.create_page_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });

        return view;
    }

    private void savePost() {
        pb.setVisibility(View.VISIBLE);
        cancelBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        final Post newPost = new Post();
        newPost.setTitle(titleTIEV.getText().toString());
        newPost.setDescription(descriptionTIEV.getText().toString());
        newPost.setTime(timeEV.getText().toString());
        newPost.setType(typeTIEV.getText().toString());
        newPost.setLocation(locationTIEV.getText().toString());
        newPost.setSpacialNeeds(specialNeedsTIEV.getText().toString());
        BitmapDrawable drawable = (BitmapDrawable)imageIV.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        newPost.setAuthorId(currUser.getId());

        Model.instance.uploadImage(bitmap, newPost.getId(), new Model.UploadImageListener() {
            @Override
            public void onComplete(String url) {
                if (url == null) {
                    displayFailedError();
                } else {
                    newPost.setImage(url);
                    Model.instance.AddPost(newPost, new Model.AddPostListener() {
                        @Override
                        public void onComplete() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("TAG","add post menu");
        menu.clear();
        inflater.inflate(R.menu.add_post_menu, menu);
    }

    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageIV.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);

                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageIV.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }

                    break;
            }
        }
    }
}