package com.studio.mpak.orshankanews.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.utils.HtmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementLoader extends AsyncTaskLoader<ArrayList<Announcement>> {

    private static final String LOG_TAG = AnnouncementLoader.class.getSimpleName();
    private static final String URL = "http://www.orshanka.by/?page_id=22085";


    public AnnouncementLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Announcement> loadInBackground() {
        Document document = null;
        try {
            document = Jsoup.connect(URL).timeout(10000).get();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return HtmlParser.extractAnnouncement(document);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
