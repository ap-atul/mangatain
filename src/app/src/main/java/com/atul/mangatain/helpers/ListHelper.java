package com.atul.mangatain.helpers;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import java.util.ArrayList;
import java.util.List;

public class ListHelper {

    public static List<Manga> fromRMangaList(List<com.atul.readm.model.Manga> mangas) {
        List<Manga> mangaList = new ArrayList<>();

        for (com.atul.readm.model.Manga m : mangas) {
            mangaList.add(new Manga(
                    m.title,
                    m.url,
                    m.summary,
                    m.rating,
                    m.art,
                    m.tags
            ));
        }

        return mangaList;
    }

    public static Manga fromRManga(com.atul.readm.model.Manga m) {
        Manga manga = new Manga(
                    m.title,
                    m.url,
                    m.summary,
                    m.rating,
                    m.art,
                    m.tags
            );
        manga.chapters = fromRChapterList(m.chapters);
        manga.author = m.author;
        manga.status = m.status;
        return manga;
    }

    public static com.atul.readm.model.Manga toRManga(Manga manga) {
        return new com.atul.readm.model.Manga(
                manga.title,
                manga.url,
                manga.summary,
                manga.rating,
                manga.art,
                manga.tags
        );
    }

    public static List<Chapter> fromRChapterList(List<com.atul.readm.model.Chapter> chapters) {
        List<Chapter> chapterList = new ArrayList<>();

        for (com.atul.readm.model.Chapter c : chapters) {
            chapterList.add(new Chapter(
                    c.title,
                    c.url,
                    c.publication,
                    c.pages
            ));
        }

        return chapterList;
    }

    public static com.atul.readm.model.Chapter toRChapter(Chapter chapter) {
        return new com.atul.readm.model.Chapter(
                chapter.title,
                chapter.url,
                chapter.publication,
                chapter.pages
        );
    }

    public static Chapter fromRChapter(com.atul.readm.model.Chapter chapter) {
        return new Chapter(
                chapter.title,
                chapter.url,
                chapter.publication,
                chapter.pages
        );
    }
}
