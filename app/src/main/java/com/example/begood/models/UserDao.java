package com.example.begood.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<Post> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertUser(User user);

    @Delete
    void deleteUser(User user);
}