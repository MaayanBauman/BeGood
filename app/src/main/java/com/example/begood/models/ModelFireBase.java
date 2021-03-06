package com.example.begood.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFireBase {
    public void getAllPosts(Long lastUpdated, final Model.GetAllPostsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp timestamp = new Timestamp(lastUpdated,0);

        db.collection("posts")
                .whereGreaterThanOrEqualTo("lastUpdated", timestamp)
                .get()
                .addOnCompleteListener(task -> {
            List<Post> data = new LinkedList<Post>();

            if (task.isSuccessful()) {
                for (DocumentSnapshot doc: task.getResult()) {
                    Post post = new Post();
                    post.fromMap(doc.getData());
                    data.add(post);
                }
            }

            listener.onComplete(data);
        });
    }

    public void addPost(Post post, Model.AddPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(post.getId())
                .set(post.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","post added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding post :(");
                listener.onComplete();
            }
        });
    }

    public void deletePost(Post post, Model.AddPostListener listener) {
        post.setIsDeleted(true);
        addPost(post, listener);
    }

    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

    public void addUser(User user, Model.AddUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId())
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","user added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding user :(");
                listener.onComplete();
            }
        });
    }

    public void updateUser(User user, Model.AddUserListener listener) {
        addUser(user, listener);
    }

    public void getUserById(@NonNull String id, Model.GetUserByIdListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).get().addOnCompleteListener(task -> {
            User user = null;

            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();

                if (doc != null) {
                    user = task.getResult().toObject(User.class);
                }
            }

            listener.onComplete(user);
        });
    }

    public void getUserRegisteredPosts(@NonNull String userId, final Model.GetUserRegisteredPostsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get().addOnCompleteListener(userTask -> {
            User user = null;
            List<String> data = new LinkedList<String>();

            if (userTask.isSuccessful()) {
                DocumentSnapshot doc = userTask.getResult();

                if (doc != null) {
                    user = userTask.getResult().toObject(User.class);

                    for (String postId: user.getRegisteredPosts()) {
                        data.add(postId);
                    }
                }
            }

            listener.onComplete(data);
        });
    }
}
