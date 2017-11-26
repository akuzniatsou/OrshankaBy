package com.studio.mpak.orshankanews.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.studio.mpak.orshankanews.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Map<String, String> tabs = new LinkedHashMap<>();
    private List<String> titles = new ArrayList<>();

    public CategoryPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.context = ctx;
        initMap();
        initTitles();
    }

    private void initMap() {
        tabs.put(context.getString(R.string.settings_content_main), "1034");
        tabs.put(context.getString(R.string.settings_content_actual), "74");
        tabs.put(context.getString(R.string.settings_content_society), "3639");
        tabs.put(context.getString(R.string.settings_content_culture), "154");
        tabs.put(context.getString(R.string.settings_content_official), "1067");
        tabs.put(context.getString(R.string.settings_content_event), "2278");
        tabs.put(context.getString(R.string.settings_content_economic), "1906");
        tabs.put(context.getString(R.string.settings_content_heals), "48");
        tabs.put(context.getString(R.string.settings_content_sport), "24");
        tabs.put(context.getString(R.string.settings_content_citizen), "3651");
        tabs.put(context.getString(R.string.settings_content_photo), "3623");
        tabs.put(context.getString(R.string.settings_content_partnership), "3668");
        tabs.put(context.getString(R.string.settings_content_group), "3664");
        tabs.put(context.getString(R.string.settings_content_anniversary), "3667");
    }

    private void initTitles() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        for (String next : tabs.keySet()) {
            boolean isShow = sharedPrefs.getBoolean(next, true);
            if (isShow) {
                titles.add(next);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        String title = titles.get(position);
        String url = tabs.get(title);
        Fragment fragment = new ArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
