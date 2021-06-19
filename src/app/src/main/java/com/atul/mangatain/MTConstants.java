package com.atul.mangatain;

public class MTConstants {

    public static final String BASE_URL = "https://www.readm.org/";

    public static final int[] TAB_ICONS = new int[]{
            R.drawable.ic_browse,
            R.drawable.ic_library,
    };

    public static final String DEBUG_TAG = "tain_debug";

    public static enum PageViewMode {
        ASPECT_FILL(0),
        ASPECT_FIT(1),
        FIT_WIDTH(2);

        private PageViewMode(int n) {
            native_int = n;
        }
        public final int native_int;
    }
}
