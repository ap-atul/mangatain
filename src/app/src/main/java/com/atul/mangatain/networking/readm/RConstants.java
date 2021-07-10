package com.atul.mangatain.networking.readm;

import java.util.HashMap;

class RConstants {

	public static final String BASE_URL = "https://www.readm.org/";

	// string format, [page]
	public static final String BROWSE = "https://www.readm.org/popular-manga/%d";

	// string format, [category name, page]
	public static final String BROWSE_CAT = "https://www.readm.org/category/%s/%d";

	// string format [manga url, page no]
	public static final String CHAPTER_URL = "https://www.readm.org/%s/%d/all-pages";

	// user agent
	public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";

	// headers
	public static final HashMap<String, String> HEADERS = new HashMap<String, String> () {{
		put("Cache-Control", "public max-age=604800");
	}};

	// time out
	public static final int TIMEOUT = 10000;

	// all genres
	public static HashMap<String, String> getGenres() {
		HashMap<String, String> genres = new HashMap<>();
		genres.put("Action", "action");
		genres.put("Adventure", "adventure");
		genres.put("Comedy", "comedy");
		genres.put("Doujinshi", "doujinshi");
		genres.put("Drama", "drama");
		genres.put("Ecchi", "ecchi");
		genres.put("Fantasy", "fantasy");
		genres.put("Gender Bender", "gender-bender");
		genres.put("Harem", "harem");
		genres.put("Historical", "historical");
		genres.put("Horror", "horror");
		genres.put("Josei", "josei");
		genres.put("Lolicon", "lolicon");
		genres.put("Manga", "manga");
		genres.put("Manhua", "manhua");
		genres.put("Manhwa", "manhwa");
		genres.put("Martial Arts", "martial-arts");
		genres.put("Mecha", "mecha");
		genres.put("Mystery", "mystery");
		genres.put("None", "none");
		genres.put("One shot", "one-shot");
		genres.put("Psychological", "psychological");
		genres.put("Romance", "romance");
		genres.put("School Life", "school-life");
		genres.put("Sci fi", "sci-fi");
		genres.put("Seinen", "seinen");
		genres.put("Shotacon", "shotacon");
		genres.put("Shoujo", "shoujo");
		genres.put("Shoujo Ai", "shoujo-ai");
		genres.put("Shounen", "shounen");
		genres.put("Shounen Ai", "shounen-ai");
		genres.put("Slice of Life", "slice-of-life");
		genres.put("Sports", "sports");
		genres.put("Supernatural", "supernatural");
		genres.put("Tragedy", "tragedy");
		genres.put("Uncategorized", "uncategorized");
		genres.put("Yaoi", "yaoi");
		genres.put("Yuri", "yuri");

		return genres;
	}
}
