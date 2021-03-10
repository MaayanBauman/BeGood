package com.example.begood.models;

import android.os.AsyncTask;

import java.util.List;

public class ModelSQL {
    public interface GetAllPostsListener{ void onComplete(List<Post> data); }
    public void getAllPosts(final Model.GetAllPostsListener listener) {
        class MyAsyncTask extends AsyncTask {
            List<Post> data;

            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.postDao().getAllPosts();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }

        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddPostListener { void onComplete(); }
    public void AddPost(Post post, final Model.AddPostListener listener) {
        class MyAsyncTask2 extends AsyncTask {
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

        MyAsyncTask2 task2 = new MyAsyncTask2();
        task2.execute();
    }
}
