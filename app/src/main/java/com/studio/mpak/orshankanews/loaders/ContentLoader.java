package com.studio.mpak.orshankanews.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.utils.HtmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class ContentLoader extends AsyncTaskLoader<List<Article>> {

    private static final String LOG_TAG = ContentLoader.class.getSimpleName();
    private String url;

    public ContentLoader(String url, Context context) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Article> loadInBackground() {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return HtmlParser.extractArticles(document);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
