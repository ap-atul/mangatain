package com.atul.mangatain;

import java.util.Arrays;
import java.util.List;

public class MTConstants {

    public static final String BASE_URL = "https://www.readm.org/";

    public static final int[] TAB_ICONS = new int[]{
            R.drawable.ic_browse,
            R.drawable.ic_library,
            R.drawable.ic_search,
            R.drawable.ic_setting
    };

    public static final String DEBUG_TAG = "tain_debug";

    // database constants
    public static final String MANGA_DB = "manga_db";
    public static final String MANGA_TABLE = "manga";
    public static final String NOVEL_TABLE = "novel";

    public static final String[] OOPS = new String[] {
            "¯\\_(ツ)_/¯",
            "[¬º-°]¬",
            "¯\\_ಠ_ಠ_/¯",
            "¯\\_(ツ)_/¯",
            "ಥ_ಥ",
            "(ø_ø)",
            "＼（〇_ｏ）／"
    };
    public static final int DATABASE_VERSION = 3;


    public static final String SETTINGS_THEME = "shared_pref_theme";
    public static final String SETTINGS_THEME_MODE = "shared_pref_theme_mode";
    public static final List<Integer> ACCENT_LIST = Arrays.asList(
            R.color.red,
            R.color.pink,
            R.color.purple,
            R.color.deep_purple,
            R.color.red,
            R.color.indigo,
            R.color.blue,
            R.color.light_blue,
            R.color.cyan,
            R.color.teal,
            R.color.green,
            R.color.light_green,
            R.color.lime,
            R.color.yellow,
            R.color.amber,
            R.color.orange,
            R.color.deep_orange,
            R.color.brown,
            R.color.grey,
            R.color.blue_grey,

            R.color.red_300,
            R.color.pink_300,
            R.color.purple_300,
            R.color.deep_purple_300,
            R.color.red_300,
            R.color.indigo_300,
            R.color.blue_300,
            R.color.light_blue_300,
            R.color.cyan_300,
            R.color.teal_300,
            R.color.green_300,
            R.color.light_green_300,
            R.color.lime_300,
            R.color.yellow_300,
            R.color.amber_300,
            R.color.orange_300,
            R.color.deep_orange_300,
            R.color.brown_300,
            R.color.grey_300,
            R.color.blue_grey_300
    );
    public static final String PACKAGE_NAME = "com.atul.mangatain";
    public static final String GITHUB_REPO_URL = "https://github.com/AP-Atul/mangatain/";
}
