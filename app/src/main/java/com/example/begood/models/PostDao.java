package com.example.begood.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from posts order by time asc")
    List<Post> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertPost(Post post);

    @Delete
    void deletePost(Post post);
}
