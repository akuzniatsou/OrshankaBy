package com.studio.mpak.orshankanews.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Announcement;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.domain.Vacancy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class HtmlParser {

    private static final String LOG_TAG = HtmlParser.class.getSimpleName();
    // Сжатие картинок под экран
    public static final String IMAGE_STYLE = "<style>img{display: inline;height: auto;max-width: 100%;}</style>";
    // Сжатие iframe video под экран
    public static final String VIDEO_STYLE = "<style>iframe{max-width: 100%;max-height: 56.25vw;}</style>";
    public static final String TEXT_STYLE = "<style>{text-align:justify;}</style>";
    public static final String NEW_LINE = "\n";


    private HtmlParser() {
    }

    public static Article fetchArticle(Document document) {
        if (document == null) {
            return null;
        }
        Article article = new Article();

        document.select("script,.hidden,style").remove();
        document.select(".wp-caption").attr("style", "width: 100%;max-width: 100%;");
        Element content = document.getElementById("content");

        Elements hrefs = content.select(".cat-links > a[href]");
        for (Element href : hrefs) {
            article.getCategories().add(href.text());
        }

        String textShorted = content.select(".entry-content").text().substring(0,100) + "...";
        String title = content.select(".entry-title").text();
        article.setTitle(title);
        String articleUrl = content.select(".posted-on").select("a").attr("href");
        article.setArticleUrl(articleUrl);
        String articleId = articleUrl.substring(articleUrl.lastIndexOf("=") + 1);
        try {
            article.setId(Integer.valueOf(articleId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Failed to get article id of " + articleUrl);
            return null;
        }
        String imageUrl = content.select("img").attr("src");
        article.setImageUrl(imageUrl);

        String date = content.select(".posted-on").text();
        article.setDate(date);
        String views = content.select(".post-views").text();
        article.setViews(views);

        content.select(".posted-on > a").prepend("Опубликовано: ").append("<br/>").removeAttr("href");
        content.select(".post-views > a").prepend("Просмотров: ").removeAttr("href");
        content.select(".author, .comments, .cat-links, .uptolike-buttons").remove();


        String htmlMain = "<html prefix=\"og: http://ogp.me/ns#\"><head><title>" + article.getTitle()+ "</title>\n" +
                IMAGE_STYLE + NEW_LINE + VIDEO_STYLE + NEW_LINE + TEXT_STYLE + NEW_LINE +
                "</head>"+ NEW_LINE +
                "<body>"+ NEW_LINE +
                content.html()+ NEW_LINE +
                "</body>"+ NEW_LINE +
                "</html>";

        article.setContent(htmlMain);
        return article;
    }

    public static Article fetchArticleFull(Document document) {
        if (document == null) {
            return null;
        }
        Article article = new Article();
        article.setContent(document.html());
        return article;
    }

    public static ArrayList<Article> extractArticles(Document document) {
        if (document == null) {
            return new ArrayList<>();
        }
        document.select("script,.hidden,style").remove();
        HashMap<Integer, Article> articleHashMap = new LinkedHashMap<>();
        ArrayList<Article> articleList = new ArrayList<>();

        Elements items = document.select(".post");
        for (Element item : items) {
            Article article = getArticle(item);
            if (article == null) continue;
            articleList.add(article);
            articleHashMap.put(article.getId(), article);
        }
        articleList = new ArrayList<>(articleHashMap.values());

        return articleList;
    }

    @Nullable
    private static Article getArticle(Element item) {
        Article article = new Article();
        Elements select = item.select(".cat-links");
        Elements hrefs = select.select("a[href]");
        for (Element href : hrefs) {
            article.getCategories().add(href.text());
        }
        article.setTitle(item.select(".entry-title").text());
        String articleUrl = item.select(".entry-title > a").attr("href");
        article.setArticleUrl(articleUrl);
        String articleId = articleUrl.substring(articleUrl.lastIndexOf("=") + 1);
        try {
            article.setId(Integer.valueOf(articleId));
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Failed to get article id of " + articleUrl);
            return null;
        }
        article.setImageUrl(item.select("img").attr("src"));
        article.setDate(item.select(".posted-on").text());
        article.setContent(item.select(".entry-content").text());
        return article;
    }

    public static ArrayList<Announcement<String>> extractAnnouncement(Document document) {
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


    public static ArrayList<Announcement<Vacancy>> extractVacancy(Document document) {
        if (document == null) {
            return null;
        }

        document.select("script,.hidden,style, a, img, posts").remove();
        Element content = document.getElementById("post-5342");

        Element element = content.select("p").last();
        ArrayList<Announcement<Vacancy>> announcements = new ArrayList<>();
        Announcement<Vacancy> announcement = null;
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
