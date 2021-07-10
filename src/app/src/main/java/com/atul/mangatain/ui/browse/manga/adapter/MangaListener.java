package com.atul.mangatain.ui.browse.manga.adapter;

import com.atul.mangatain.model.Manga;

public interface MangaListener {
    void click(Manga manga);
    void loadMore();
}
