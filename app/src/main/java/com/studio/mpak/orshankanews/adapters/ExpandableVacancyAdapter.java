package com.studio.mpak.orshankanews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.domain.Vacancy;

import java.util.ArrayList;
import java.util.Iterator;

public class ExpandableVacancyAdapter extends BaseExpandableListAdapter {

    private ArrayList<Announcement<Vacancy>> announcements;
    private ArrayList<Announcement<Vacancy>> origin;
    private Context context;

    public ExpandableVacancyAdapter(Context context, ArrayList<Announcement<Vacancy>> announcements) {
        this.context = context;
        this.announcements = new ArrayList<>(announcements);
        origin = new ArrayList<>(announcements);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return announcements.get(groupPosition).getEvents().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vacancy_item, null);
        }

        Vacancy vacancy = announcements.get(groupPosition).getEvents().get(childPosition);
        TextView tvPosition = convertView.findViewById(R.id.position);
        tvPosition.setText(String.format("● %s", vacancy.getPosition()));
        TextView tvSalary = convertView.findViewById(R.id.salary);
        tvSalary.setText(vacancy.getSalary());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return announcements.get(groupPosition).getEvents().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return announcements.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return announcements.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.announcement_header, null);
        }

        TextView textGroup = convertView.findViewById(R.id.header);
        Announcement header = announcements.get(groupPosition);
        textGroup.setText(header.getPlace());

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public void addAll(ArrayList<Announcement<Vacancy>> data) {
        announcements.addAll(data);
        origin.addAll(data);
    }

    public void clear() {
        announcements = new ArrayList<>();
        origin = new ArrayList<>();
    }

    public void filter(CharSequence filter) {
        String query = filter.toString().toLowerCase();
        announcements.clear();
        if (query.isEmpty()) {
            announcements.addAll(origin);
        }
        for (Announcement<Vacancy> announcement : origin) {
            if (announcement.getPlace().toLowerCase().contains(query)) {
                announcements.add(announcement);
                continue;
            }
            Iterator<Vacancy> iterator = announcement.getEvents().iterator();
            while (iterator.hasNext()) {
                Vacancy vacancy = iterator.next();
                if ((vacancy.getPosition().toLowerCase().contains(query))
                    || vacancy.getSalary().toLowerCase().contains(query)) {
                    announcements.add(announcement);
                } else {
                    iterator.remove();
                }
            }
        }
        notifyDataSetChanged();
    }
}