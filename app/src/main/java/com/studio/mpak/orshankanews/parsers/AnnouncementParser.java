package com.studio.mpak.orshankanews.parsers;

import com.studio.mpak.orshankanews.domain.Announcement;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementParser implements DocumentParser<ArrayList<Announcement<String>>> {

    @Override
    public ArrayList<Announcement<String>> parse(Document document) {
        if (document == null) {
            return null;
        }

        document.select("script,.hidden,style, a, img").remove();
//        document.select(":containsOwn(\u00a0)").remove();
        Element content = document.getElementById("content");

        for (Element element : content.select("*")) {
            if (!element.hasText() && element.isBlock()) {
                element.remove();
            }
        }
        content.select("h4").first().remove();
        content.select("h4").tagName("p");
        content.select("p").first().remove();
        Elements select = content.select("p");
        ArrayList<Announcement<String>> announcements = new ArrayList<>();
        Announcement<String> announcement = null;
        for (Element element : select) {
            List<Node> nodes = element.childNodes();
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
                            announcement.getEvents().add(event);
                        }
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
