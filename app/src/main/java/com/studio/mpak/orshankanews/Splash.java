package com.studio.mpak.orshankanews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ContentLoader;

import java.util.ArrayList;

public class Splash extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private String uri;
    public static final String NEWS_URL = "http://www.orshanka.by/";
    public static final int CONTENT_LOADER_ID = 0;
    private ArrayList<Article> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            uri = NEWS_URL;
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(CONTENT_LOADER_ID, null, this);
        } else {
            ImageView splashScreen = findViewById(R.id.splash_image);
            splashScreen.setVisibility(View.GONE);
            TextView emptyState = findViewById(R.id.empty_view);
            emptyState.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        return new ContentLoader(uri, Splash.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        this.data = data;
        Intent intent = new Intent(Splash.this, MainActivity.class);
        intent.putParcelableArrayListExtra("list", this.data);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        this.data.clear();
        loader.abandon();
    }


}
