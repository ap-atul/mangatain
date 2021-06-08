package com.atul.mangatain.networking;

import androidx.lifecycle.MutableLiveData;

import com.atul.readm.controller.RClient;
import com.atul.readm.controller.RListener;
import com.atul.readm.model.Chapter;
import com.atul.readm.model.Manga;

import java.util.List;
import java.util.Set;

public class RMRepository implements RListener {
    MutableLiveData<List<Manga>> mangas = null;
    RClient client = null;

    public RMRepository(){
        client = new RClient(this);
    }

    public MutableLiveData<List<Manga>>  getMangas(){
        if(mangas == null)
            mangas = new MutableLiveData<>();

        return mangas;
    }

    public void browse(int page, String genre){
        client.browse(page, genre);
    }

    public void search(String query){
        client.search(query);
    }

    public void chapters(Manga manga){
        client.chapters(manga);
    }

    public void pages(Chapter chapter){
        client.pages(chapter);
    }

    public Set<String> genres(){
        return client.genres();
    }

    @Override
    public void setMangas(List<Manga> list) {
        mangas.postValue(list);
    }

    @Override
    public void setChapters(Manga manga) {

    }

    @Override
    public void setPages(Chapter chapter) {

    }
}
