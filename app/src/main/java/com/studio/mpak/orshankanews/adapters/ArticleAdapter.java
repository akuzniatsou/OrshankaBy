package com.studio.mpak.orshankanews.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayout.LayoutParams;
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

        TextView title = view.findViewById(R.id.title);
        title.setText(article.getTitle());

        ImageView imageView = view.findViewById(R.id.img);
        String link = getEncodedUrl(article.getImageUrl());
        if (link == null || TextUtils.isEmpty(link)) {
            // Default image if missing
//            Glide.with(getContext()).load(R.drawable.image_missing).into(imageView);
            imageView.setVisibility(View.GONE);
        } else {
            Glide.with(getContext()).load(link).into(imageView);
        }

        TextView date = view.findViewById(R.id.date);
        date.setText(article.getDate());

        // Hashtags
//        FlexboxLayout linearLayout = view.findViewById(R.id.tags);
//        linearLayout.removeAllViews();
//        for (String category : article.getCategories()) {
//            int categoryColor = getTagColor(category);
//
//            TextView categoryView = new TextView(getContext());
//
//            categoryView.setBackgroundResource(R.drawable.round_rect_shape);
//            ((GradientDrawable)categoryView.getBackground()).setColor(categoryColor);
//            categoryView.setText(String.format("#%s", category));
//            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            params.setMargins(5,0,5,0);
//            categoryView.setLayoutParams(params);
//            categoryView.setPadding(2,2,2,2);
//            categoryView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorCategory));
//            categoryView.setTextSize(10);
//            linearLayout.addView(categoryView);
//        }

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


    private int getTagColor(String category) {
        int color;
        switch (category) {
            case "Актуально":
                color = R.color.colorActual;
                break;
            case "Общество":
                color = R.color.colorSociety;
                break;
            case "Культура":
                color = R.color.colorCulture;
                break;
            case "Фоторепортажи":
                color = R.color.colorPhoto;
                break;
            case "Официально":
                color = R.color.colorOfficial;
                break;
            case "Экономика":
                color = R.color.colorEconomic;
                break;
            case "Спорт":
                color = R.color.colorSport;
                break;
            case "Молодежь":
                color = R.color.colorYouth;
                break;
            default:
                color = R.color.colorActual;
                break;
        }
        return ContextCompat.getColor(getContext(), color);
    }

}
