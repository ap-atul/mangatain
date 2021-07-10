package com.atul.mangatain.networking.novel;

import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Novel;
import com.atul.mangatain.model.NovelChapter;

import java.util.List;
import java.util.Set;

public class NClient {
    public static void browse(MutableLiveData<List<Novel>> novels, String genre, int page) {
        new Thread() {
            @Override
            public void run() {
                NLoader.browse(novels, genre, page);
            }
        }.start();
    }

    public static void search(MutableLiveData<List<Novel>> novels, String query) {
        new Thread() {
            @Override
            public void run() {
                NLoader.search(novels, query);
            }
        }.start();
    }

    public static void novel(MutableLiveData<Novel> mutableNovel, Novel novel) {
        new Thread() {
            @Override
            public void run() {
                NLoader.novel(mutableNovel, novel);
            }
        }.start();
    }

    public static void page(MutableLiveData<NovelChapter> mutableChapter, NovelChapter chapter) {
        new Thread() {
            @Override
            public void run() {
                NLoader.page(mutableChapter, chapter);
            }
        }.start();
    }

    public static Set<String> genres() {
        return NConstant.genres.keySet();
    }
}
