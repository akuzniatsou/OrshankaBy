package com.studio.mpak.orshankanews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.studio.mpak.orshankanews.adapters.ExpandableAnnouncementAdapter;
import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.loaders.AnnouncementLoader;

import java.util.ArrayList;

public class AnnouncementActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Announcement<String>>> {

    private ExpandableAnnouncementAdapter mAdapter;
    private ProgressBar bar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list_activity);

        ExpandableListView exListView = findViewById(R.id.list_expandable);
        bar = findViewById(R.id.loading);
        bar.setVisibility(View.VISIBLE);
        mAdapter = new ExpandableAnnouncementAdapter(this, new ArrayList<Announcement<String>>());
        exListView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<ArrayList<Announcement<String>>> onCreateLoader(int id, Bundle args) {
        return new AnnouncementLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Announcement<String>>> loader,
                               ArrayList<Announcement<String>> data) {
        bar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            mAdapter.clear();
            mAdapter.addAll(data);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Announcement<String>>> loader) {
        mAdapter.clear();
    }
}


