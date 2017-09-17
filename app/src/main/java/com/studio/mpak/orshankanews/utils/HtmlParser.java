package com.studio.mpak.orshankanews.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.studio.mpak.orshankanews.domain.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HtmlParser {

    private static final String LOG_TAG = HtmlParser.class.getSimpleName();
    // Сжатие картинок под экран
    public static final String IMAGE_STYLE = "<style>img{display: inline;height: auto;max-width: 100%;}</style>";
    // Сжатие iframe video под экран
    public static final String VIDEO_STYLE = "<style>iframe{max-width: 100%;max-height: 56.25vw;}</style>";


    private HtmlParser() {
    }

    public static Article fetchArticle(Document document) {
        if (document == null) {
            return null;
        }
        Article response = new Article();

        document.select("script,.hidden,style").remove();
        document.select(".wp-caption").attr("style", "width: 100%;max-width: 100%;");
        Element content = document.getElementById("content");
        content.select(".entry-content").attr("style", "text-align:justify;");

        Elements select = content.select(".cat-links");
        Elements hrefs = select.select("a[href]");
        for (Element href : hrefs) {
            response.getCategories().add(href.text());
        }
        response.setTitle(content.select(".entry-title").text());
        String articleUrl = content.select("a").attr("href");
        response.setArticleUrl(articleUrl);
        String articleId = articleUrl.substring(articleUrl.lastIndexOf("=") + 1);
        try {
            response.setId(Integer.valueOf(articleId));
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Failed to get article id of " + articleUrl);
            return null;
        }
        response.setImageUrl(content.select("img").attr("src"));

        content.select(".posted-on > a").prepend("Опубликовано: ").append("<br/>").removeAttr("href");
        content.select(".post-views > a").prepend("Просмотров: ").removeAttr("href");

        response.setDate(content.select(".posted-on").text());
        response.setViews(content.select(".post-views").text());

        content.select(".author, .comments, .cat-links").remove();
        response.setContent(IMAGE_STYLE + VIDEO_STYLE + content.html());

//            response = content.select(".entry-content").html();

        return response;
    }

    public static List<Article> extractArticles(Document document) {
        if (document == null) {
            return Collections.emptyList();
        }
        document.select("script,.hidden,style").remove();
//        document.select("header, footer").remove();
//        document.select(".front-page-top-section").remove();
        HashMap<Integer, Article> articleHashMap = new LinkedHashMap<>();
        List<Article> articleList = new ArrayList<>();
//        Elements items = document.getElementsByClass("single-article");
        Elements items = document.select(".single-slide");
        for (Element item : items) {
            Article article = getArticle(item);
            if (article == null) continue;
            articleList.add(article);
            articleHashMap.put(article.getId(), article);
        }

        items = document.select(".post");
        for (Element item : items) {
            Article article = getArticle(item);
            if (article == null) continue;
            articleList.add(article);
            articleHashMap.put(article.getId(), article);
        }

        items = document.select(".single-article");
        for (Element item : items) {
            Article article = getArticle(item);
            if (article == null) continue;
            articleList.add(article);
            articleHashMap.put(article.getId(), article);
        }


        return new ArrayList<>(articleHashMap.values());
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
//            String articleUrl = item.select("a").attr("href");
//            article.setArticleUrl(articleUrl);
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
}
