package com.studio.mpak.orshankanews.parsers;

import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.domain.Vacancy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.*;

public class VacancyParser implements DocumentParser<ArrayList<Announcement<Vacancy>>> {

    @Override
    public ArrayList<Announcement<Vacancy>> parse(Document document) {
        if (document == null) {
            return null;
        }

        document.select("script,.hidden,style, a, img, posts").remove();
        Element content = document.getElementById("post-5342");

        Elements elements = content.select("p");
        TreeMap<Integer, Element> elementMap = new TreeMap<>();
        for (Element element : elements) {
            // Prepare map with length of elements
            elementMap.put(element.text().length(), element);
        }


        ArrayList<Announcement<Vacancy>> announcements = new ArrayList<>();
        // Element with max length is vacancy section
        Element vacancyElement = elementMap.lastEntry().getValue();
        Announcement<Vacancy> announcement = null;
        List<Node> nodes = vacancyElement.childNodes();
        for (Node node : nodes) {
            if (node.nodeName().equals("strong")) {
                if (announcement != null) {
                    announcements.add(announcement);
                }
                announcement = new Announcement<>();
                announcement.setPlace(((Element)node).text());

            } else {
                if (node instanceof TextNode) {
                    String event = ((TextNode) node).text();
                    event = event.trim();
                    if (!event.equals("")) {
                        int index = event.lastIndexOf(" ");
                        String position = event.substring(0, index);
                        String salary = event.substring(index + 1, event.length());

                        announcement.getEvents().add(new Vacancy(position, salary));
                    }
                }
            }

        }

        if (announcement != null) {
            announcements.add(announcement);
        }
        return announcements;
    }
}
