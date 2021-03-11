package com.example.begood.models;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSQL {
    public LiveData<List<Post>> getAllPosts() {
        return AppLocalDb.db.postDao().getAllPosts();
    }

    public interface AddPostListener { void onComplete(); }
    public void AddPost(Post post, final Model.AddPostListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.postDao().InsertPost(post);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                if (listener != null){
                    listener.onComplete();
                }
            }
        }

        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
