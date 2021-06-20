package com.atul.mangatain.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.helpers.ListHelper;
import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.ui.detail.TagAdapter;
import com.atul.readm.controller.RClient;
import com.atul.readm.controller.RListener;

import java.util.List;
import java.util.Set;

public class RMRepository extends ViewModel implements RListener {
    MutableLiveData<List<Manga>> mangas = null;
    MutableLiveData<Manga> chapters = null;
    MutableLiveData<Chapter> pages = null;
    RClient client;
    static int page  = 1;

    public RMRepository() {
        client = new RClient(this);
    }

    public MutableLiveData<List<Manga>>  getMangas(){
        if(mangas == null)
            mangas = new MutableLiveData<>();

        return mangas;
    }

    public MutableLiveData<Manga> getChapters(){
        if(chapters == null)
            chapters = new MutableLiveData<>();

        return chapters;
    }

    public MutableLiveData<Chapter> getPages(){
        if(pages == null)
            pages = new MutableLiveData<>();

        return pages;
    }

    public void browse(String genre){
        client.browse(page, genre);
        page++;
    }

    public void search(String query){
        client.search(query);
    }

    public void chapters(Manga manga){
        client.chapters(ListHelper.toRManga(manga));
    }

    public void pages(Chapter chapter){
        client.pages(ListHelper.toRChapter(chapter));
    }

    public Set<String> genres(){
        return client.genres();
    }

    @Override
    public void setMangas(List<com.atul.readm.model.Manga> list) {
        mangas.postValue(ListHelper.fromRMangaList(list));
    }

    @Override
    public void setChapters(com.atul.readm.model.Manga manga) {
        chapters.postValue(ListHelper.fromRManga(manga));
    }

    @Override
    public void setPages(com.atul.readm.model.Chapter chapter) {
        pages.postValue(ListHelper.fromRChapter(chapter));
    }
}
