package com.atul.mangatain.networking.novel;


import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Novel;
import com.atul.mangatain.model.NovelChapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NLoader {
    public static void browse(MutableLiveData<List<Novel>> novelMutableLiveData, String genre, int page) {
        String baseUrl = NApiBuilder.browse(genre, page);
        List<Novel> novels = new ArrayList<>();

        try {

            Element doc = Jsoup.connect(baseUrl).headers(NConstant.HEADERS).userAgent(NConstant.USER_AGENT).get().body();
            for (Element novel : doc.select("div[class=top-novel-block]")) {
                String title = novel.select("div[class=top-novel-header]").select("a").text();
                String url = novel.select("div[class=top-novel-header]").select("a").attr("href");
                String art = novel.select("img").attr("src");

                novels.add(new Novel(url, title, art, null, null, null, null, null, null));
            }

            if(novelMutableLiveData != null)
                novelMutableLiveData.postValue(novels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void novel(MutableLiveData<Novel> novelMutableLiveData, Novel novel){
        String baseUrl = novel.url;
        try {
            Element doc = Jsoup.connect(baseUrl).headers(NConstant.HEADERS).userAgent(NConstant.USER_AGENT).get().body();

            String description = null;
            String rating = null;
            StringBuilder author = new StringBuilder();
            String status = null;
            List<String> tags = new ArrayList<>();
            List<NovelChapter> chapters = new ArrayList<>();

            for(Element item : doc.select("div[class=novel-details]").select("div[class=novel-detail-item]")) {
                String header = item.select("div[class=novel-detail-header]").select("h6").text();

                if(header.equals("Description"))
                    description = item.select("div[class=novel-detail-body]").text();

                if(header.equals("Rating"))
                    rating = item.select("div[class=novel-detail-body]").text();

                if(header.equals("Genre"))
                    for (Element gen : item.select("li"))
                        tags.add(gen.text());

                if(header.equals("Author(s)"))
                    for (Element aut: item.select("li"))
                        author.append(" ").append(aut.text());

                if(header.equals("Status"))
                    status = item.select("li").text();

            }

            for(Element chp: doc.select("div[class=tab-content]").select("li")){
                String title = chp.select("a").text();
                String url = chp.select("a").attr("href");

                chapters.add(new NovelChapter(url, title, null));
            }

            novel.chapters =  chapters;
            novel.description = description;
            novel.rating = rating;
            novel.status = status;
            novel.author = author.toString();
            novel.tags = tags;

            if(novelMutableLiveData != null)
                novelMutableLiveData.postValue(novel);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void page(MutableLiveData<NovelChapter> novelChapterMutableLiveData, NovelChapter chapter){
        String baseUrl = chapter.url;
        try {

            Element doc = Jsoup.connect(baseUrl).headers(NConstant.HEADERS).userAgent(NConstant.USER_AGENT).get().body();
            chapter.content = doc.select("div[id=growfoodsmart]").html();

            if(novelChapterMutableLiveData != null)
                novelChapterMutableLiveData.postValue(chapter);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void search(MutableLiveData<List<Novel>> novelMutable, String query){
        try {
            List<Novel> novels = new ArrayList<>();
            HashMap<String, String> data = new HashMap<>();
            data.put("q", query);
            Element doc = Jsoup.connect(NConstant.SEARCH_URL).userAgent(NConstant.USER_AGENT).headers(NConstant.HEADERS).data(data).post().body();

            for(Element novel : doc.select("li")){
                String url  = novel.select("a").attr("href");
                String art = novel.select("img").attr("src");
                String title = novel.select("span[class=title]").text();

                novels.add(new Novel(url, title, art, null, null, null, null, null, null));
            }

            if(novelMutable != null)
                novelMutable.postValue(novels);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
