package com.studio.mpak.orshankanews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.studio.mpak.orshankanews.R;
import com.studio.mpak.orshankanews.domain.Announcement;

import java.util.ArrayList;

public class ExpandableAnnouncementAdapter extends BaseExpandableListAdapter {

    private ArrayList<Announcement> announcements;
    private Context context;

    public ExpandableAnnouncementAdapter(Context context, ArrayList<Announcement> announcements) {
        this.context = context;
        this.announcements = announcements;
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
            convertView = inflater.inflate(R.layout.announcement_item, null);
        }

        TextView textChild = convertView.findViewById(R.id.child);
        String tvChild = announcements.get(groupPosition).getEvents().get(childPosition);
        textChild.setText(String.format("● %s", tvChild));

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

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
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


    public void addAll(ArrayList<Announcement> data) {
        announcements.addAll(data);
    }

    public void clear() {
        announcements = new ArrayList<>();
    }
}