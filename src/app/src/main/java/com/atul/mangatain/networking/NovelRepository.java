package com.atul.mangatain.networking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.model.Novel;
import com.atul.mangatain.networking.novel.NClient;

import java.util.List;

public class NovelRepository extends ViewModel {
    MutableLiveData<List<Novel>> novels = new MutableLiveData<>();
    MutableLiveData<List<Novel>> search = new MutableLiveData<>();

    public MutableLiveData<List<Novel>> browse(String genre, int page){
        NClient.browse(novels, genre, page);
        return novels;
    }

    public MutableLiveData<List<Novel>> search(String query){
        NClient.search(search, query);
        return search;
    }
}
