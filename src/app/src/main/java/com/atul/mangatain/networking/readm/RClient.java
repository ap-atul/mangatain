package com.atul.mangatain.networking.readm;

import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import java.util.List;
import java.util.Set;

public class RClient {

    // genre can be null
    public static void browse(MutableLiveData<List<Manga>> mangas, int page, String genre) {
        new Thread() {
            @Override
            public void run() {
                RLoader.browse(mangas, page, genre);
            }
        }.start();
    }

    // search for manga by keyword
    public static void search(MutableLiveData<List<Manga>> search, String query) {
        new Thread() {
            @Override
            public void run() {
                RLoader.search(search, query);
            }
        }.start();
    }

    public static void details(MutableLiveData<Manga> detail, Manga manga) {
        new Thread() {
            @Override
            public void run() {
                RLoader.getMoreDetails(detail, manga);
            }
        }.start();
    }

    // get chapters
    public static void chapters(MutableLiveData<List<Chapter>> chapters, Manga manga) {
        new Thread() {
            @Override
            public void run() {
                RLoader.getChapters(chapters, manga);
            }
        }.start();
    }

    // get pages
    public static void pages(MutableLiveData<List<String>> pages, Chapter chapter) {
        new Thread() {
            @Override
            public void run() {
                RLoader.getPages(pages, chapter);
            }
        }.start();
    }

    // get all genres
    public static Set<String> genres() {
        return RConstants.getGenres().keySet();
    }
}
