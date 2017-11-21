package com.studio.mpak.orshankanews;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;

import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ArticleLoader;

public class WebViewActivity extends Activity implements LoaderManager.LoaderCallbacks<Article> {

    private Article article;
    private WebView webView;
    private String articleUrl;
    private View buttonView;
    private ScrollView scrollView;
    public static final int ARTICLE_LOADER_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_main);
        webView = findViewById(R.id.webView1);
        buttonView = findViewById(R.id.soc_share_panel);
        scrollView = findViewById(R.id.scrollView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                buttonView.setVisibility(View.VISIBLE);
                scrollView.scrollTo(0,0);
            }
        });

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
            return super.onOptionsItemSelected(item);
   }

    public void fbShareAction(View view) {
        Uri uri = Uri.parse("https://www.facebook.com/sharer.php").buildUpon()
                .appendQueryParameter("u", articleUrl).build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

    public void okShareAction(View view) {
        Uri uri = Uri.parse("https://connect.ok.ru/offer").buildUpon()
                .appendQueryParameter("url", articleUrl).build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

    public void vkShareAction(View view) {
        Uri uri = Uri.parse("https://vk.com/share.php").buildUpon()
                .appendQueryParameter("url", articleUrl).build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

    public void tweetShareAction(View view) {
        Uri uri = Uri.parse("https://twitter.com/intent/tweet").buildUpon()
                .appendQueryParameter("url", articleUrl).build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(uri);
        startActivity(i);
    }

}