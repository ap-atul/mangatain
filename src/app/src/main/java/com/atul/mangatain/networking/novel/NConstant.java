package com.atul.mangatain.networking.novel;

import java.util.HashMap;

public class NConstant {
    public static final String BROWSE_URL = "https://www.readlightnovel.org/top-novel/%d";

    public static final String BROWSE_GENRE = "https://www.readlightnovel.org/category/%s/%d";

    public static final String SEARCH_URL = "https://www.readlightnovel.org/search/autocomplete";

    // headers
    public static final HashMap<String, String> HEADERS = new HashMap<String, String> () {{
        put("Cache-Control", "public max-age=604800");
        put("x-requested-with", "XMLHttpRequest");
    }};
    // user agent
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";

    // all genres
    public static final HashMap<String, String> genres = new HashMap<String, String>() {{
        put("Action", "action");
        put("Adventure", "adventure");
        put("Comedy", "comedy");
        put("Doujinshi", "doujinshi");
        put("Drama", "drama");
        put("Ecchi", "ecchi");
        put("Fantasy", "fantasy");
        put("Gender Bender", "gender-bender");
        put("Harem", "harem");
        put("Historical", "historical");
        put("Horror", "horror");
        put("Josei", "josei");
        put("Lolicon", "lolicon");
        put("Manhua", "manhua");
        put("Manhwa", "manhwa");
        put("Martial Arts", "martial-arts");
        put("Mecha", "mecha");
        put("Mystery", "mystery");
        put("None", "none");
        put("One shot", "one-shot");
        put("Psychological", "psychological");
        put("Romance", "romance");
        put("School Life", "school-life");
        put("Sci fi", "sci-fi");
        put("Seinen", "seinen");
        put("Shotacon", "shotacon");
        put("Shoujo", "shoujo");
        put("Shoujo Ai", "shoujo-ai");
        put("Shounen", "shounen");
        put("Shounen Ai", "shounen-ai");
        put("Slice of Life", "slice-of-life");
        put("Sports", "sports");
        put("Supernatural", "supernatural");
        put("Tragedy", "tragedy");
        put("Yaoi", "yaoi");
        put("Yuri", "yuri");
    }};

}
