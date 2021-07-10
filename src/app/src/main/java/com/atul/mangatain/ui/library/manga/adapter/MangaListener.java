package com.atul.mangatain.ui.library.manga.adapter;

import com.atul.mangatain.model.Manga;

public interface MangaListener {
    void click(Manga manga);
    void remove(Manga manga);
}
