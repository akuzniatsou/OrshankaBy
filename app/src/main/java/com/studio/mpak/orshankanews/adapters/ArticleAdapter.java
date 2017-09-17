package com.studio.mpak.orshankanews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.domain.Article;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    public ArticleAdapter(Context context, List<Article> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        Article article = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        TextView category = view.findViewById(R.id.category);
        category.setText(article.getCategories().toString());

        TextView title = view.findViewById(R.id.title);
        title.setText(article.getTitle());

        ImageView img = view.findViewById(R.id.img);
        String link = getEncodedUrl(article.getImageUrl());
        if (link == null || TextUtils.isEmpty(link)) {
            Picasso.with(getContext()).load(R.drawable.image_missing).into(img);
        } else {
            Picasso.with(getContext()).load(link).error(R.drawable.image_missing).into(img);
        }

        TextView date = view.findViewById(R.id.date);
        date.setText(article.getDate());

        return view;
    }

    private String getEncodedUrl(String url) {
        String link = null;
        try {
            String path = url.substring(0, url.lastIndexOf("/") + 1);
            String lastPath = url.substring(url.lastIndexOf("/") + 1);
            link = path + URLEncoder.encode(lastPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Error with encoding URL", e);
            e.printStackTrace();
        }
        return link;
    }
}
