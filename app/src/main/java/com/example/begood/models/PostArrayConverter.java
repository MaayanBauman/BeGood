package com.example.begood.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PostArrayConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Post> stringToPostsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Post>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String PostsListToString(List<Post> someObjects) {
        return gson.toJson(someObjects);
    }
}