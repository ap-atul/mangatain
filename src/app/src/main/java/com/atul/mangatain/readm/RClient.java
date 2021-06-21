package com.atul.mangatain.readm;

import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import java.util.Set;

public class RClient {

    // genre can be null
    public static MutableLiveData<Manga> browse(int page, String genre) {
        MutableLiveData<Manga> data = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                RLoader.browse(data, page, genre);
            }
        }.start();

        return data;
    }

    // search for manga by keyword
    public static MutableLiveData<Manga> search(String query) {
        MutableLiveData<Manga> data = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                RLoader.search(data, query);
            }
        }.start();

        return data;
    }

    public static MutableLiveData<Manga> details(Manga manga) {
        MutableLiveData<Manga> data = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                RLoader.getMoreDetails(data, manga);
            }
        }.start();

        return data;
    }

    // get chapters
    public static MutableLiveData<Chapter> chapters(Manga manga) {
        MutableLiveData<Chapter> data = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                RLoader.getChapters(data, manga);
            }
        }.start();

        return data;
    }

    // get pages
    public static MutableLiveData<String> pages(Chapter chapter) {
        MutableLiveData<String> data = new MutableLiveData<>();
        new Thread() {
            @Override
            public void run() {
                RLoader.getPages(data, chapter);
            }
        }.start();

        return data;
    }

    // get all genres
    public static Set<String> genres() {
        return RConstants.getGenres().keySet();
    }
}
