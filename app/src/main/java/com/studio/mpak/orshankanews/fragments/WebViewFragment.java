package com.studio.mpak.orshankanews.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ArticleLoader;

public class WebViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Article> {

    private WebView webView;
    private String articleUrl;
    public static final int ARTICLE_LOADER_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.web_main, container, false);
        webView = v.findViewById(com.studio.mpak.orshankanews.R.id.webView1);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        articleUrl = getArguments().getString(ArticleEntry.COLUMN_URL);
        getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
        return v;
    }

    @Override
    public Loader<Article> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(articleUrl, getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<Article> loader, Article article) {
        webView.loadDataWithBaseURL(null, article.getContent(),"text/html", "UTF-8", null);
    }

    @Override
    public void onLoaderReset(Loader<Article> loader) {
        webView.loadUrl("about:blank");
    }

}
