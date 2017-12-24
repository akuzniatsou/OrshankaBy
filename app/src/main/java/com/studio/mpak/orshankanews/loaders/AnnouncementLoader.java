package com.studio.mpak.orshankanews.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.parsers.DocumentParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementLoader extends AsyncTaskLoader<ArrayList<Announcement<String>>> {

    private static final String LOG_TAG = AnnouncementLoader.class.getSimpleName();
    private static final String URL = "http://www.orshanka.by/?page_id=22085";
    private final DocumentParser<ArrayList<Announcement<String>>> parser;


    public AnnouncementLoader(Context context, DocumentParser<ArrayList<Announcement<String>>> parser) {
        super(context);
        this.parser = parser;
    }

    @Override
    public ArrayList<Announcement<String>> loadInBackground() {
        Document document = null;
        try {
            document = Jsoup.connect(URL).timeout(10000).get();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return parser.parse(document);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
