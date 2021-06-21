package com.atul.mangatain.networking;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.readm.RClient;

import java.util.Set;

public class RMRepository extends ViewModel {
    MutableLiveData<Manga> mangas = null;
    MutableLiveData<Chapter> chapters = null;
    MutableLiveData<String> pages = null;
    MutableLiveData<Manga> search = null;
    static int page  = 1;

    public MutableLiveData<Manga> browse(String genre){
        page++;
        mangas = RClient.browse(page, genre);
        return mangas;
    }

    public MutableLiveData<Manga> details(Manga manga){
        mangas = RClient.details(manga);
        return mangas;
    }

    public MutableLiveData<Manga> search(String query){
        search = RClient.search(query);
        return search;
    }

    public MutableLiveData<Chapter> chapters(Manga manga){
        chapters = RClient.chapters(manga);
        return chapters;
    }

    public MutableLiveData<String> pages(Chapter chapter){
        pages = RClient.pages(chapter);
        return pages;
    }

    public Set<String> genres(){
        return RClient.genres();
    }
}
