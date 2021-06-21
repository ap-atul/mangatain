package com.atul.mangatain.readm;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import java.util.List;


public interface RListener {

	void setMangas(List<Manga> mangas);

	void setChapters(Manga manga);

	void setPages(Chapter chapter);
}
