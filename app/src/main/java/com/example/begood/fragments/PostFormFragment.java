package com.example.begood.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.begood.R;
import com.example.begood.models.Model;
import com.example.begood.models.Post;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PostFormFragment extends Fragment {
    // Fragment members
    TextInputEditText titleTIEV;
    TextInputEditText descriptionTIEV;
    TextInputEditText specialNeedsTIEV;
    TextInputEditText typeTIEV;
    Button imageBtn;
    ImageView imageIV;
    EditText dateET;
    TextInputEditText locationTIEV;
    ProgressBar pb;
    Button cancelBtn;
    Button saveBtn;
    View view;
    Post updatedPost;
    String defaultValue = "empty :(";
    Bitmap bitmap;
    DatePickerDialog picker;

    public PostFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_form, container, false);
        updatedPost = PostFormFragmentArgs.fromBundle(getArguments()).getPost();
        setHasOptionsMenu(true);

        // Fragment members
        titleTIEV = view.findViewById(R.id.create_page_filed_title_input);
        descriptionTIEV = view.findViewById(R.id.create_page_filed_description_input);
        imageBtn = view.findViewById(R.id.create_page_filed_image_btn);
        imageIV = view.findViewById(R.id.create_page_filed_image);
        typeTIEV = view.findViewById(R.id.create_page_filed_type_input);
        locationTIEV = view.findViewById(R.id.create_page_filed_location_input);
        specialNeedsTIEV = view.findViewById(R.id.create_page_filed_needs_input);
        dateET = view.findViewById(R.id.create_page_filed_date_input);
        dateET.setInputType(InputType.TYPE_NULL);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(204, 153, 255));
                picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(204, 153, 255));
            }
        });
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(PostFormFragmentDirections.actionGlobalFeedFrg());
            }
        });
        // Save post
        saveBtn = view.findViewById(R.id.create_page_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });

        if (updatedPost.getTitle() != null) {
            TextView pageTitle = view.findViewById(R.id.create_page_title);
            pageTitle.setText("Update volunteer");
            titleTIEV.setText(updatedPost.getTitle());
            descriptionTIEV.setText(updatedPost.getDescription());
            Picasso.get().load(updatedPost.getImage()).into(imageIV);
            typeTIEV.setText(updatedPost.getType());
            locationTIEV.setText(updatedPost.getLocation());
            specialNeedsTIEV.setText(updatedPost.getSpacialNeeds());
            dateET.setText(updatedPost.getDate());
            saveBtn.setText("Update");
        }

        return view;
    }

    private void savePost() {
        pb.setVisibility(View.VISIBLE);
        cancelBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        updatedPost.setTitle(getInputValue(titleTIEV));
        updatedPost.setDescription(getInputValue(descriptionTIEV));
        updatedPost.setType(getInputValue(typeTIEV));
        updatedPost.setLocation(getInputValue(locationTIEV));
        updatedPost.setSpacialNeeds(getInputValue(specialNeedsTIEV));
        BitmapDrawable drawable = (BitmapDrawable)imageIV.getDrawable();

        if(dateET.getText().toString().isEmpty()){
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            updatedPost.setDate(currentDate);
        } else {
            updatedPost.setDate(dateET.getText().toString());
        }
        if(drawable == null){
            int w = 90, h = 45;
            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            bitmap = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        } else {
            bitmap = drawable.getBitmap();
        }

        Model.instance.uploadImage(bitmap, updatedPost.getId(), new Model.UploadImageListener() {
            @Override
            public void onComplete(String url) {
                if (url == null) {
                    displayFailedError();
                } else {
                    updatedPost.setImage(url);
                    Model.instance.AddPost(updatedPost, new Model.AddPostListener() {
                        @Override
                        public void onComplete() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
                }
            }
        });
    }

    public String getInputValue(TextInputEditText item) {
        if(item.getText().toString().isEmpty()) {
            return defaultValue;
        }
        return item.getText().toString();
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