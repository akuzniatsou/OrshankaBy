package com.studio.mpak.orshankanews;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ArticleLoader;

public class WebViewActivity extends Activity implements LoaderManager.LoaderCallbacks<Article> {

    private WebView webView;
    private String articleUrl;
    public static final int ARTICLE_LOADER_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.studio.mpak.orshankanews.R.layout.web_main);
        webView = findViewById(com.studio.mpak.orshankanews.R.id.webView1);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        articleUrl = getIntent().getStringExtra(ArticleEntry.COLUMN_URL);
        getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Article> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(articleUrl, WebViewActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Article> loader, Article article) {
        webView.loadDataWithBaseURL(null, article.getContent(),"text/html", "UTF-8", null);
    }

    @Override
    public void onLoaderReset(Loader<Article> loader) {
        webView.loadUrl("about:blank");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}