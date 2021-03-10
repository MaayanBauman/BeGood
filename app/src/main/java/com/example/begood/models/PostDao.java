package com.example.begood.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from posts order by date asc")
    LiveData<List<Post>> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertPost(Post post);

    @Delete
    void deletePost(Post post);
}
