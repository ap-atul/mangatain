package com.atul.mangatain.ui.library.novel.adapter;

import com.atul.mangatain.model.Novel;

public interface NovelListener {
    void click(Novel novel);
    void remove(Novel novel);
}
