package com.studio.mpak.orshankanews.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.WebViewActivity;
import com.studio.mpak.orshankanews.adapters.ArticleAdapter;
import com.studio.mpak.orshankanews.data.ArticleContract;
import com.studio.mpak.orshankanews.domain.Article;

import java.util.ArrayList;

public class ArticleFragment extends Fragment {

    private ArticleAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.article_list, container, false);

        ArrayList<Article> articles = getArguments().getParcelableArrayList("list");
        if (articles == null) {
            articles = new ArrayList<>();
        }
        mAdapter = new ArticleAdapter(getActivity(), articles);

        final ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article article = (Article) listView.getItemAtPosition(position);
                Uri webPage = Uri.parse(article.getArticleUrl());
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra(ArticleContract.ArticleEntry.COLUMN_URL, webPage.toString());
                startActivity(intent);
                mAdapter.setNotifyOnChange(false);
            }

        });

        return rootView;
    }
}
