package com.atul.mangatain.database.coverters;

import androidx.room.TypeConverter;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.NovelChapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

public class CustomConverters {
    private static final Gson gson = new Gson();

    public static class ChapterConverter{
        @TypeConverter
        public static List<Chapter> strToChapter(String data){
            if(data == null) return Collections.emptyList();
            return gson.fromJson(data, new TypeToken<List<Chapter>>() {}.getType());
        }

        @TypeConverter
        public static String chapterToStr(List<Chapter> chapters){
            return gson.toJson(chapters);
        }
    }

    public static class NovelChapterConverter{
        @TypeConverter
        public static List<NovelChapter> strToChapter(String data){
            if(data == null) return Collections.emptyList();
            return gson.fromJson(data, new TypeToken<List<NovelChapter>>() {}.getType());
        }

        @TypeConverter
        public static String chapterToStr(List<NovelChapter> chapters){
            return gson.toJson(chapters);
        }
    }

    public static class StringConverter{
        @TypeConverter
        public static List<String> strToStr(String data){
            if(data == null) return Collections.emptyList();
            return gson.fromJson(data, new TypeToken<List<String>>() {}.getType());
        }

        @TypeConverter
        public static String strToStr(List<String> data){
            return gson.toJson(data);
        }
    }
}
