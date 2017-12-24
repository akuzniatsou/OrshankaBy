package com.studio.mpak.orshankanews.validators;

import com.studio.mpak.orshankanews.domain.Article;
import org.jsoup.helper.StringUtil;

public class ShortArticleValidator implements Validator<Article> {

    @Override
    public boolean isValid(Article domain) {
        String date = domain.getDate();
        String title = domain.getTitle();
        return !StringUtil.isBlank(date) && !StringUtil.isBlank(title);
    }
}
