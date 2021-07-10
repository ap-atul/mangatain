package com.atul.mangatain.ui.pager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atul.mangatain.model.NovelChapter;
import com.atul.mangatain.networking.novel.NClient;

public class PagerViewModel extends ViewModel {

    private final MutableLiveData<NovelChapter> chapter = new MutableLiveData<>();

    public MutableLiveData<NovelChapter> chapter(NovelChapter chp){
        NClient.page(chapter, chp);
        return chapter;
    }
}
