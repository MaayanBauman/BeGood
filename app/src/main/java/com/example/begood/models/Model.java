package com.example.begood.models;

import android.os.AsyncTask;

import java.util.List;

public class Model {
    public final static Model instance = new Model();

    private Model() {
    }

    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }
    public void getAllPosts(GetAllPostsListener listener) {
        class MyAsyncTask extends AsyncTask {
            List<Post> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.postDao().getAll();
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

    public interface AddPostListener {
        void onComplete();
    }
    public void AddPost(Post post, AddPostListener listener) {
        class MyAsyncTask2 extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                post.setId("id2");
                AppLocalDb.db.postDao().InsertPost(post);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(listener != null){
                    listener.onComplete();
                }
            }
        }
        MyAsyncTask2 task2 = new MyAsyncTask2();
        task2.execute();
    }

//    public interface GetPostByIdListener{
//        void onComplete(Post data);
//    }
//    public void getPostById(String postId, GetAllPostsListener listener) {
//        class MyAsyncTask3 extends AsyncTask {
//            Post post;
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                post = AppLocalDb.db.postDao().getById(postId);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//                listener.onComplete(post);
//            }
//        }
//        MyAsyncTask3 task = new MyAsyncTask3();
//        task.execute();
//    }
}
