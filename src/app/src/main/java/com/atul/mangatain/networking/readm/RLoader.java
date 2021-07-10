package com.atul.mangatain.networking.readm;

import androidx.lifecycle.MutableLiveData;

import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class RLoader {

	public static void browse(MutableLiveData<List<Manga>> mutableManga, int page, String genre) {
		try {
			String pageUrl;
			List<Manga> mangaList = new ArrayList<>();

			if (genre == null)
				pageUrl = RApiBuilder.buildBrowse(page);
			else
				pageUrl = RApiBuilder.buildCatBrowse(page, genre);

			Element doc = Jsoup.connect(pageUrl).headers(RConstants.HEADERS).userAgent(RConstants.USER_AGENT).get().body();
			for (Element manga : doc.select("li[class=mb-lg]")) {
				Elements e = manga.select("div[class=subject-title]").select("a");
				String title = e.attr("title");
				String url = e.attr("href");
				String summary = manga.select("p[class=desktop-only excerpt]").text();
				String[] data = manga.select("div[class=color-imdb]").text().split(" ");
				String rating = null;
				if(data.length >= 2)
					rating = data[1];

				String art;
				if(genre == null)
					art = manga.select("div[class=poster-with-subject]").select("img").attr("src");
				else
					art = manga.select("div[class=poster-with-subject]").select("img").attr("data-src");

				List<String> tags = new ArrayList<>();
				Elements aa = manga.select("span[class=genres]").select("a");
				for (Element tag : aa) {
					tags.add(tag.attr("title"));
				}

				Manga m = new Manga(title, url, summary, rating, art, tags);
				mangaList.add(m);
			}

			mutableManga.postValue(mangaList);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getChapters(MutableLiveData<List<Chapter>> chapters, Manga manga) {
		try {
			List<Chapter> chapterList = new ArrayList<>();
			Elements doc = Jsoup.connect(RApiBuilder.buildCombo(manga.url)).headers(RConstants.HEADERS).userAgent(RConstants.USER_AGENT).get()
					.body().select("section[class=episodes-box]")
					.select("table[class=ui basic unstackable table]");

			for (Element chp : doc) {
				String title = chp.select("a").text();
				String url = chp.select("a").attr("href");
				String pub = chp.select("td[class=episode-date]").text();


				chapterList.add(new Chapter(title, url, pub, null));
			}
			manga.chapters = chapterList;
			chapters.postValue(chapterList);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getPages(MutableLiveData<List<String>> data, Chapter chapter) {
		try {
			List<String> pages = new ArrayList<>();
			Elements doc = Jsoup.connect(RApiBuilder.buildCombo(chapter.url)).headers(RConstants.HEADERS).userAgent(RConstants.USER_AGENT).get()
					.body().select("img[class=img-responsive scroll-down]");

			for (Element pg : doc) {
				String page = pg.attr("src");
				pages.add(page);
			}

			data.postValue(pages);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void search(MutableLiveData<List<Manga>> mutableManga, String query) {
		try {
			List<Manga> mangaList = new ArrayList<>();
			HashMap<String, String> data = new HashMap<>();
			data.put("dataType", "json");
			data.put("phrase", query);

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X-Requested-With", "XMLHttpRequest");
			headers.put("Cache-Control", "public max-age=604800");
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
				mangaList.add(m);
			}

			mutableManga.postValue(mangaList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getMoreDetails(MutableLiveData<Manga> data, Manga manga) {
		String author = null, status = null;

		try {
			Element doc = Jsoup.connect(RApiBuilder.buildCombo(manga.url)).headers(RConstants.HEADERS).userAgent(RConstants.USER_AGENT).get()
					.body();

			author = doc.select("div[class=sub-title pt-sm]").text();
			status = doc.select("span[class=series-status aqua]").text();

			if(manga.summary.equals(""))
				manga.summary = doc.select("div[class=series-summary-wrapper]").select("p").select("span").text();

			if(manga.rating.equals("0"))
				manga.rating = doc.select("div[class=color-imdb]").text();

			if(manga.tags == null) {
				manga.tags = new ArrayList<>();
				for (Element gen : doc.select("div[class=series-summary-wrapper]").select("div[class=ui list]").select("a"))
					manga.tags.add(gen.attr("title"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		manga.author = author;
		manga.status = status;
		data.postValue(manga);
	}
}
