package com.example.begood.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFireBase {
    public void getAllPosts(final Model.GetAllPostsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").get().addOnCompleteListener(task -> {
            List<Post> data = new LinkedList<Post>();

            if (task.isSuccessful()){
                for (DocumentSnapshot doc: task.getResult()) {
                    Post post = doc.toObject(Post.class);
                    data.add(post);
                }
            }

            listener.onComplete(data);
        });
    }

    public void getPostById(@NonNull String id, Model.GetPostByIdListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(id).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Post post = null;

                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc != null) {
                        post = task.getResult().toObject(Post.class);
                    }
                }

                listener.onComplete(post);
            }
        });
    }

    public void addPost(Post post, Model.AddPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(post.getId())
                .set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void delete(String postId, Model.DeleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(postId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
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

    public void updateUser(User user, Model.UpdateUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId())
                .set(user).addOnSuccessListener(aVoid -> {
                    Log.d("TAG","user update successfully");
                    listener.onComplete();
                }).addOnFailureListener(e -> {
                    Log.d("TAG","fail update user :(");
                    listener.onComplete();
                });
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
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            User user = null;
            List<Post> data = new LinkedList<Post>();

            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                if (doc != null) {
                    user = task.getResult().toObject(User.class);

                    for (Post post: user.getRegisteredPosts()) {
                        data.add(post);
                    }
                }
            }

            listener.onComplete(data);
        });
    }

    public void GetUserUploadPosts(@NonNull String userId, final Model.GetUserUploadPostsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").get().addOnCompleteListener(task -> {
            List<Post> data = new LinkedList<Post>();

            if (task.isSuccessful()){
                for (DocumentSnapshot doc: task.getResult()) {
                    Post post = doc.toObject(Post.class);

                    if (post.getAuthorId().compareTo(userId) == 0) {
                        data.add(post);
                    }
                }
            }

            listener.onComplete(data);
        });
    }
}
