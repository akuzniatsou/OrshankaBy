package com.studio.mpak.orshankanews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.domain.Announcement;

import java.util.List;

public class AnnouncementAdapter extends ArrayAdapter<Announcement> {

    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();
    private static final String LIST_ITEM = "\u25cf";

    public AnnouncementAdapter(Context context, List<Announcement> announcements) {
        super(context, 0, announcements);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Announcement announcement = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.announcement_item, parent, false);
        }

//        TextView tvPlace = view.findViewById(R.id.place);
//        tvPlace.setText(announcement.getPlace());
//        TextView tvEvent = view.findViewById(R.id.event);
//        StringBuilder builder = new StringBuilder();
//        for (String event : announcement.getEvents()) {
//            builder.append(LIST_ITEM).append(" ").append(event).append("\r\n\r\n");
//        }
//        String allEvents = builder.toString().trim();
//        tvEvent.setText(allEvents);


        return view;
    }
}
