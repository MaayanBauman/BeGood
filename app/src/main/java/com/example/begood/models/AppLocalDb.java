package com.example.begood.models;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.begood.MyApplication;

@Database(entities = { Post.class, User.class }, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "posts.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

