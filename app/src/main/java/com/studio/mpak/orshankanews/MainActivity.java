package com.studio.mpak.orshankanews;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.studio.mpak.orshankanews.adapters.ArticleAdapter;
import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.data.ArticleContract.ArticleEntry;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.fragments.WebViewFragment;
import com.studio.mpak.orshankanews.loaders.ArticleLoader;
import com.studio.mpak.orshankanews.loaders.ContentLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String NEWS_URL = "http://www.orshanka.by/";
    public static final int CONTENT_LOADER_ID = 0;


    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    private ArticleAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private SwipeRefreshLayout refreshLayout;
    private String uri;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.studio.mpak.orshankanews.R.layout.activity_main);

        final ListView listView = (ListView) findViewById(com.studio.mpak.orshankanews.R.id.list);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(com.studio.mpak.orshankanews.R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                Article article = (Article) listView.getItemAtPosition(position);
//                Uri webPage = Uri.parse(article.getArticleUrl());
//
//                WebViewFragment fragment = new WebViewFragment();
//                Bundle args = new Bundle();
//                args.putString(ArticleEntry.COLUMN_URL, webPage.toString());
//                fragment.setArguments(args);
//                getFragmentManager().beginTransaction().replace(R.id.main, fragment, TAG_FRAGMENT).addToBackStack(null).commit();


                Article article = (Article) listView.getItemAtPosition(position);
                Uri webPage = Uri.parse(article.getArticleUrl());
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(ArticleEntry.COLUMN_URL, webPage.toString());
                startActivity(intent);
                mAdapter.setNotifyOnChange(false);
            }

        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                mAdapter.setNotifyOnChange(true);
                uri = "http://www.orshanka.by/?m=201708&paged=" + page;
                getLoaderManager().restartLoader(CONTENT_LOADER_ID, null, MainActivity.this);
                page++;
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            uri = NEWS_URL;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(CONTENT_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText(com.studio.mpak.orshankanews.R.string.no_internet_connection);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ContentLoader(uri, MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
//        for (Article article : data) {
//            ContentValues values = new ContentValues();
//            values.put(ArticleEntry.COLUMN_TITLE, article.getTitle());
//            values.put(ArticleEntry.COLUMN_URL, article.getArticleUrl());
//            values.put(ArticleEntry.COLUMN_SCR_IMAGE, article.getImageUrl());
//            values.put(ArticleEntry.COLUMN_PUB_DATE, article.getDate());
//            values.put(ArticleEntry._ID, article.getId());
//            getContentResolver().insert(ArticleEntry.CONTENT_URI, values);
//        }

        mEmptyStateTextView.setText(com.studio.mpak.orshankanews.R.string.no_earthquakes);
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

}
