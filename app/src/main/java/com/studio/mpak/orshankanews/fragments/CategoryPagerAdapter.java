package com.studio.mpak.orshankanews.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Map<String, Fragment> tabs = new LinkedHashMap<String, Fragment>(){{
        put("Актуально", new ArticleFragment());
        put("Главное", new ArticleFragment());
        put("Здоровье", new ArticleFragment());
        put("Культура", new ArticleFragment());
        put("Молодежь", new ArticleFragment());
        put("Общество", new ArticleFragment());
        put("Оршанцы", new ArticleFragment());
        put("Официально", new ArticleFragment());
        put("Спорт", new ArticleFragment());
        put("Экономика", new ArticleFragment());
    }};

    private List<String> titles = new ArrayList<>(tabs.keySet());

    public CategoryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        String title = titles.get(position);
        return tabs.get(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        List<String> titles = new ArrayList<>(tabs.keySet());
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}

/*
<option value='-1'>Выберите рубрику</option>
<option class="level-0" value="3666" selected="selected">100 лет &#171;АГ&#187;&nbsp;&nbsp;(6)</option>
<option class="level-0" value="74">Актуально&nbsp;&nbsp;(361)</option>
<option class="level-0" value="1">Архив&nbsp;&nbsp;(3 009)</option>
<option class="level-0" value="1034">Главное&nbsp;&nbsp;(166)</option>
<option class="level-0" value="48">Здоровье&nbsp;&nbsp;(206)</option>
<option class="level-0" value="154">Культура&nbsp;&nbsp;(605)</option>
<option class="level-1" value="1122">&nbsp;&nbsp;&nbsp;Духовное&nbsp;&nbsp;(100)</option>
<option class="level-1" value="1163">&nbsp;&nbsp;&nbsp;Хобби&nbsp;&nbsp;(54)</option>
<option class="level-0" value="2278">Молодежь&nbsp;&nbsp;(279)</option>
<option class="level-1" value="1072">&nbsp;&nbsp;&nbsp;Молодые специалисты&nbsp;&nbsp;(26)</option>
<option class="level-1" value="3642">&nbsp;&nbsp;&nbsp;Образование&nbsp;&nbsp;(135)</option>
<option class="level-0" value="3639">Общество&nbsp;&nbsp;(934)</option>
<option class="level-1" value="3641">&nbsp;&nbsp;&nbsp;Безопасность&nbsp;&nbsp;(134)</option>
<option class="level-1" value="3640">&nbsp;&nbsp;&nbsp;Благотворительность&nbsp;&nbsp;(44)</option>
<option class="level-1" value="3665">&nbsp;&nbsp;&nbsp;Год науки&nbsp;&nbsp;(14)</option>
<option class="level-1" value="3650">&nbsp;&nbsp;&nbsp;Демография&nbsp;&nbsp;(119)</option>
<option class="level-1" value="1025">&nbsp;&nbsp;&nbsp;Коммунальное хозяйство&nbsp;&nbsp;(61)</option>
<option class="level-1" value="3648">&nbsp;&nbsp;&nbsp;Криминал&nbsp;&nbsp;(41)</option>
<option class="level-1" value="3643">&nbsp;&nbsp;&nbsp;Происшествия&nbsp;&nbsp;(33)</option>
<option class="level-1" value="940">&nbsp;&nbsp;&nbsp;Экология&nbsp;&nbsp;(107)</option>
<option class="level-0" value="3651">Оршанцы&nbsp;&nbsp;(327)</option>
<option class="level-1" value="1049">&nbsp;&nbsp;&nbsp;Мнение&nbsp;&nbsp;(105)</option>
<option class="level-1" value="20">&nbsp;&nbsp;&nbsp;Портрет&nbsp;&nbsp;(107)</option>
<option class="level-1" value="1070">&nbsp;&nbsp;&nbsp;Юбилеи&nbsp;&nbsp;(30)</option>
<option class="level-0" value="1067">Официально&nbsp;&nbsp;(281)</option>
<option class="level-1" value="3659">&nbsp;&nbsp;&nbsp;Всебелорусское народное собрание&nbsp;&nbsp;(13)</option>
<option class="level-1" value="3660">&nbsp;&nbsp;&nbsp;Выборы&nbsp;&nbsp;(35)</option>
<option class="level-1" value="3646">&nbsp;&nbsp;&nbsp;Госконтроль&nbsp;&nbsp;(51)</option>
<option class="level-1" value="3580">&nbsp;&nbsp;&nbsp;Парламентский дневник&nbsp;&nbsp;(6)</option>
<option class="level-0" value="3664">Профсоюзы&nbsp;&nbsp;(49)</option>
<option class="level-0" value="24">Спорт&nbsp;&nbsp;(371)</option>
<option class="level-1" value="838">&nbsp;&nbsp;&nbsp;Путешествия&nbsp;&nbsp;(8)</option>
<option class="level-1" value="3649">&nbsp;&nbsp;&nbsp;Туризм и отдых&nbsp;&nbsp;(7)</option>
<option class="level-0" value="3623">Фоторепортажи&nbsp;&nbsp;(180)</option>
<option class="level-0" value="1906">Экономика&nbsp;&nbsp;(477)</option>
<option class="level-1" value="1349">&nbsp;&nbsp;&nbsp;На предприятиях&nbsp;&nbsp;(181)</option>
<option class="level-1" value="1907">&nbsp;&nbsp;&nbsp;Сельское хозяйство&nbsp;&nbsp;(136)</option>
<option class="level-1" value="3340">&nbsp;&nbsp;&nbsp;Торговля&nbsp;&nbsp;(76)</option>
<option class="level-0" value="3667">Юбилей города&nbsp;&nbsp;(21)</option>
*/