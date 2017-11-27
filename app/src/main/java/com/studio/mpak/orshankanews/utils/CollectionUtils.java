package com.studio.mpak.orshankanews.utils;

import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.domain.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

    public static ArrayList<Announcement<Vacancy>> deepCopy(ArrayList<Announcement<Vacancy>> source) {
        ArrayList<Announcement<Vacancy>> clonedList = new ArrayList<>(source.size());
        for (Announcement<Vacancy> announcement : source) {
            Announcement<Vacancy> cloned = new Announcement<>(announcement);
            cloned.setEvents(deepCopy(announcement.getEvents()));
            clonedList.add(cloned);
        }
        return clonedList;
    }

    private static List<Vacancy> deepCopy(List<Vacancy> source) {
        List<Vacancy> clonedList = new ArrayList<>(source.size());
        for (Vacancy vacancy : source) {
            clonedList.add(new Vacancy(vacancy));
        }
        return clonedList;
    }
}
