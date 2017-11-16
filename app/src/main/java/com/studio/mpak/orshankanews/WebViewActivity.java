package com.studio.mpak.orshankanews;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ShareActionProvider;

import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ArticleLoader;

public class WebViewActivity extends Activity implements LoaderManager.LoaderCallbacks<Article> {

    private WebView webView;
    private String articleUrl;
    public static final int ARTICLE_LOADER_ID = 1;
    private ShareActionProvider mShareActionProvider;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.studio.mpak.orshankanews.R.layout.web_main);
        webView = findViewById(com.studio.mpak.orshankanews.R.id.webView1);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setDomStorageEnabled(true);
//
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setWebViewClient(new WebViewClient());
        articleUrl = getIntent().getStringExtra(ArticleEntry.COLUMN_URL);
        getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Article> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(articleUrl, WebViewActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Article> loader, Article article) {
        webView.loadDataWithBaseURL(article.getArticleUrl(), article.getContent(),"text/html", "UTF-8", null);
    }

    @Override
    public void onLoaderReset(Loader<Article> loader) {
        webView.loadUrl("about:blank");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
//        getMenuInflater().inflate(R.menu.share_menu, menu);
//
//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check it out";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                setShareIntent(sharingIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}