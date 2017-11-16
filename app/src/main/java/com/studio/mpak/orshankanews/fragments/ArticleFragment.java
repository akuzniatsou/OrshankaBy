package com.studio.mpak.orshankanews.fragments;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.WebViewActivity;
import com.studio.mpak.orshankanews.adapters.ArticleAdapter;
import com.studio.mpak.orshankanews.adapters.EndlessScrollListener;
import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.loaders.ContentLoader;

import java.util.ArrayList;

public class ArticleFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Article>>{

    private static final String ARTICLE_URL = "http://www.orshanka.by/?cat=%s";
    private static final String ARTICLE_URL_PAGED = ARTICLE_URL + "&paged=%d";
    public static final int LOADER_ID = 0;
    private ArticleAdapter mAdapter;
    private String categoryId;
    private String url;
    ProgressBar bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_list, container, false);

        mAdapter = new ArticleAdapter(getActivity(), new ArrayList<Article>());

        final ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);
                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        bar = rootView.findViewById(R.id.loading);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article article = (Article) listView.getItemAtPosition(position);
                Uri webPage = Uri.parse(article.getArticleUrl());
                Intent intent = new Intent(getActivity().getApplicationContext(), WebViewActivity.class);
                intent.putExtra(ArticleContract.ArticleEntry.COLUMN_URL, webPage.toString());
                startActivity(intent);
                mAdapter.setNotifyOnChange(false);
            }

        });

        categoryId = getArguments().getString("url");
        url = String.format(ARTICLE_URL, categoryId);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        bar.setVisibility(View.VISIBLE);
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    public void loadNextDataFromApi(int offset) {
        bar.setVisibility(View.VISIBLE);
        url = String.format(ARTICLE_URL_PAGED, categoryId, offset);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        mAdapter.notifyDataSetChanged();
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        return new ContentLoader(url, getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
//        mAdapter.clear();
        bar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        mAdapter.clear();
    }
}
