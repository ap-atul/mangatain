package com.atul.mangatain.ui.library.adapter;

import com.atul.mangatain.model.Manga;

public interface MangaListener {
    void click(Manga manga);
    void remove(Manga manga);
}
