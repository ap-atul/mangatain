package com.atul.mangatain.readm;

import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


class RLoader {

	public static void browse(MutableLiveData<Manga> mutableManga, int page, String genre) {
		try {
			String pageUrl;

			if (genre == null)
				pageUrl = RApiBuilder.buildBrowse(page);
			else
				pageUrl = RApiBuilder.buildCatBrowse(page, genre);

			Element doc = Jsoup.connect(pageUrl).userAgent(RConstants.USER_AGENT).get().body();
			for (Element manga : doc.select("li[class=mb-lg]")) {
				String title = manga.select("div[class=subject-title]").select("a").attr("title");
				String url = manga.select("div[class=subject-title]").select("a").attr("href");
				String summary = manga.select("p[class=desktop-only excerpt]").text();
				String[] data = manga.select("div[class=color-imdb]").text().split(" ");
				String rating = null;
				if(data.length >= 2)
					rating = data[1];
				
				String art = manga.select("div[class=poster-with-subject]").select("img").attr("src");
				List<String> tags = new ArrayList<>();

				for (Element tag : manga.select("span[class=genres]").select("a")) {
					tags.add(tag.attr("title"));
				}

				Manga m = new Manga(title, url, summary, rating, art, tags);
				mutableManga.postValue(m);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getChapters(MutableLiveData<Chapter> mutableChapter, Manga manga) {
		try {

			Element doc = Jsoup.connect(RApiBuilder.buildCombo(manga.url)).userAgent(RConstants.USER_AGENT).get()
					.body();

			for (Element chp : doc.select("section[class=episodes-box]")
					.select("table[class=ui basic unstackable table]")) {
				String title = chp.select("a").text();
				String url = chp.select("a").attr("href");
				String pub = chp.select("td[class=episode-date]").text();

				Chapter chapter = new Chapter(title, url, pub, null);
				mutableChapter.postValue(chapter);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getPages(MutableLiveData<String> data, Chapter chapter) {
		try {

			Element doc = Jsoup.connect(RApiBuilder.buildCombo(chapter.url)).userAgent(RConstants.USER_AGENT).get()
					.body();
			for (Element pg : doc.select("img[class=img-responsive scroll-down]")) {
				String page = pg.attr("src");
				data.postValue(page);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void search(MutableLiveData<Manga> mutableManga, String query) {
		try {

			HashMap<String, String> data = new HashMap<>();
			data.put("dataType", "json");
			data.put("phrase", query);

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X-Requested-With", "XMLHttpRequest");
			String doc = Jsoup.connect("https://www.readm.org/service/search").timeout(RConstants.TIMEOUT)
					.userAgent(RConstants.USER_AGENT).ignoreHttpErrors(true).headers(headers).data(data)
					.ignoreContentType(true).post().select("body").text();

			JSONObject json = new JSONObject(doc);
			JSONArray array = json.getJSONArray("manga");

			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String title = null, url = null, art = null;

				if (obj.has("title"))
					title = obj.getString("title");

				if (obj.has("url"))
					url = obj.getString("url");

				if (obj.has("image"))
					art = obj.getString("image");

				Manga m = new Manga(title, url, null, "0", art, null);
				mutableManga.postValue(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getMoreDetails(MutableLiveData<Manga> data, Manga manga) {
		String author = null, status = null;

		try {

			Element doc = Jsoup.connect(RApiBuilder.buildCombo(manga.url)).userAgent(RConstants.USER_AGENT).get()
					.body();

			author = doc.select("div[class=sub-title pt-sm]").text();
			status = doc.select("span[class=series-status aqua]").text();} catch (IOException e) {
			e.printStackTrace();
		}

		manga.author = author;
		manga.status = status;
		data.postValue(manga);
	}
}
