package com.studio.mpak.orshankanews.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.utils.HtmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ArticleLoader extends AsyncTaskLoader<Article> {

    private static final String LOG_TAG = ArticleLoader.class.getSimpleName();
    private String url;

    public ArticleLoader(String url, Context context) {
        super(context);
        this.url = url;
    }

    @Override
    public Article loadInBackground() {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return HtmlParser.fetchArticle(document);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
