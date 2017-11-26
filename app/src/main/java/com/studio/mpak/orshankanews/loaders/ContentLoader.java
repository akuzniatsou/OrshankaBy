package com.studio.mpak.orshankanews.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.utils.HtmlParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class ContentLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private static final String LOG_TAG = ContentLoader.class.getSimpleName();
    private String url;

    public ContentLoader(String url, Context context) {
        super(context);
        this.url = url;
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        ArrayList<Article> articles = HtmlParser.extractArticles(document);
//        for (Article article : articles) {
//            ContentValues values = new ContentValues();
//            values.put(ArticleEntry._ID, article.getId());
//            values.put(ArticleEntry.COLUMN_TITLE, article.getTitle());
//            values.put(ArticleEntry.COLUMN_URL, article.getArticleUrl());
//            values.put(ArticleEntry.COLUMN_SCR_IMAGE, article.getImageUrl());
//            values.put(ArticleEntry.COLUMN_PUB_DATE, article.getDate());
//
//            //TODO Insert to db
//            getContext().getContentResolver().insert(ArticleEntry.CONTENT_URI, values);
//        }
        return articles;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
