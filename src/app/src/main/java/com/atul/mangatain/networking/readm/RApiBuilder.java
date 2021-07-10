package com.atul.mangatain.networking.readm;

import java.util.Locale;

public class RApiBuilder {

    public static String buildBrowse(int page) {
        return String.format(Locale.getDefault(), RConstants.BROWSE, page);
    }

    public static String buildCatBrowse(int page, String genre) {
        return String.format(Locale.getDefault(), RConstants.BROWSE_CAT, RConstants.getGenres().get(genre), page);
    }

    public static String buildCombo(String url) {
        return RConstants.BASE_URL.concat(url);
    }
}