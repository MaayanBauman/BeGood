package com.example.begood.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from users")
    List<User> getAllUsers();

    @Query("select * from users where _id=:userId")
    User getUserById(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertUser(User user);

    @Delete
    void deleteUser(User user);
}
