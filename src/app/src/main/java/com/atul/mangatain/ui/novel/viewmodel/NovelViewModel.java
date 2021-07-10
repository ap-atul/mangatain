package com.atul.mangatain.ui.novel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.model.Novel;
import com.atul.mangatain.model.NovelChapter;
import com.atul.mangatain.networking.novel.NClient;

public class NovelViewModel extends ViewModel {
    private final MutableLiveData<Novel> detail = new MutableLiveData<>();
    private final MutableLiveData<NovelChapter> chapter = new MutableLiveData<>();

    public MutableLiveData<Novel> detail(Novel novel){
        NClient.novel(detail, novel);
        return detail;
    }

    public MutableLiveData<NovelChapter> chapter(NovelChapter chp){
        NClient.page(chapter, chp);
        return chapter;
    }
}
