package com.atul.mangatain.networking.novel;

import java.util.Locale;

public class NApiBuilder {
    public static String browse(String genre, int page) {
        if (genre == null)
            return String.format(Locale.getDefault(), NConstant.BROWSE_URL, page);

        return String.format(Locale.getDefault(), NConstant.BROWSE_GENRE, NConstant.genres.get(genre), page);
    }
}
