package com.studio.mpak.orshankanews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;

public class ArticleDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ArticleDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shelter.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticleEntry.TABLE_NAME + " ("
                    + ArticleEntry._ID + " INTEGER PRIMARY KEY, "
                    + ArticleEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                    + ArticleEntry.COLUMN_URL + " TEXT NOT NULL, "
                    + ArticleEntry.COLUMN_SCR_IMAGE + " TEXT NOT NULL, "
                    + ArticleEntry.COLUMN_CONTENT + " TEXT, "
                    + ArticleEntry.COLUMN_PUB_DATE + " TEXT NOT NULL);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
