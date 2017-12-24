package com.studio.mpak.orshankanews.parsers;

import android.support.annotation.Nullable;
import android.util.Log;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.validators.ShortArticleValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ArticleListParser implements DocumentParser<ArrayList<Article>> {

    private static final String LOG_TAG = ArticleListParser.class.getSimpleName();
    private static final ShortArticleValidator VALIDATOR = new ShortArticleValidator();

    @Override
    public ArrayList<Article> parse(Document document) {
        if (document == null) {
            return new ArrayList<>();
        }
        document.select("script,.hidden,style").remove();
        HashMap<Integer, Article> articleHashMap = new LinkedHashMap<>();

        Elements items = document.select(".post");
        for (Element item : items) {
            Article article = getArticle(item);
            if (article == null) continue;
            if (VALIDATOR.isValid(article)) {
                articleHashMap.put(article.getId(), article);
            }
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
