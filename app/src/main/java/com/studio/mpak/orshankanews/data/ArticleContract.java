package com.studio.mpak.orshankanews.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mpak on 16.09.2017.
 */

public class ArticleContract {

    static final String CONTENT_AUTHORITY = "com.studio.mpak.orshankanews";
    private static final String SCHEME = "content://";
    private static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + CONTENT_AUTHORITY);

    public static abstract class ArticleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ArticleEntry.TABLE_NAME);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ArticleEntry.TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ArticleEntry.TABLE_NAME;

        public final static String TABLE_NAME = "articles";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE ="title";
        public final static String COLUMN_URL = "url";
        public final static String COLUMN_SCR_IMAGE = "img";
        public final static String COLUMN_PUB_DATE = "date";
        public final static String COLUMN_CONTENT = "content";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_CATEGORY = "category_id";
        public final static String COLUMN_COMMENT_COUNT = "comment_count";



    }
}
