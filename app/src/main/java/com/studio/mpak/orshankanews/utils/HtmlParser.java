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

        String htmlHead =
                "    <meta property=\"og:title\" content=\"" + article.getTitle() + "\" />\n" +
                        "    <meta property=\"og:description\" content=\"" + textShorted + "\" />\n" +
                        "    <meta property=\"og:type\" content=\"article\"/>\n" +
                        "    <meta property=\"og:url\" content=\"" + article.getArticleUrl() + "\" />\n" +
                        "    <meta property=\"og:site_name\" content=\"Оршанская газета\"/>\n" +
                        "    <meta property=\"og:image\" content=\"" + article.getImageUrl() + "\" />\n" +
                        "    <meta name=\"description\" content=\"" +textShorted+ "\" />\n"
                ;

        String okButtons = "<div id=\"ok_shareWidget\"></div>\n" +
                "<script>\n" +
                "!function (d, id, did, st, title, description, image) {\n" +
                "  var js = d.createElement(\"script\");\n" +
                "  js.src = \"https://connect.ok.ru/connect.js\";\n" +
                "  js.onload = js.onreadystatechange = function () {\n" +
                "  if (!this.readyState || this.readyState == \"loaded\" || this.readyState == \"complete\") {\n" +
                "    if (!this.executed) {\n" +
                "      this.executed = true;\n" +
                "      setTimeout(function () {\n" +
                "        OK.CONNECT.insertShareWidget(id,did,st, title, description, image);\n" +
                "      }, 0);\n" +
                "    }\n" +
                "  }};\n" +
                "  d.documentElement.appendChild(js);\n" +
                "}(document,\"ok_shareWidget\",document.URL,'{\"sz\":45,\"st\":\"rounded\",\"nc\":1,\"nt\":1}',\"\",\"\",\"\");\n" +
                "</script>";

        String vkButton = "<script type=\"text/javascript\" src=\"https://vk.com/js/api/share.js?95\" charset=\"windows-1251\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "document.write(VK.Share.button({url: \""+ articleUrl +"\"},{type: \"article\", text: \"<img src=\\\"https://vk.com/images/share_32.png\\\" width=\\\"45\\\" height=\\\"45\\\" />\"}));\n" +
                "</script>";


        String htmlMain = "<html prefix=\"og: http://ogp.me/ns#\"><head><title>" + article.getTitle()+ "</title>\n" +
                IMAGE_STYLE + NEW_LINE + VIDEO_STYLE + NEW_LINE + TEXT_STYLE + NEW_LINE
                + htmlHead + NEW_LINE +
                "</head>"+ NEW_LINE +
                "<body>"+ NEW_LINE +
                content.html()+ NEW_LINE +
                okButtons + NEW_LINE +
                vkButton + NEW_LINE +
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
}
