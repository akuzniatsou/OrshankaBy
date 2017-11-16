package com.studio.mpak.orshankanews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.studio.mpak.orshankanews.adapters.ArticleAdapter;
import com.studio.mpak.orshankanews.domain.Article;
import com.studio.mpak.orshankanews.fragments.CategoryPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    CategoryPagerAdapter fAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Article> articles = getIntent().getParcelableArrayListExtra("list");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        fAdapter = new CategoryPagerAdapter(getFragmentManager(), MainActivity.this, articles);

        viewPager.setAdapter(fAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*


        final ListView listView = (ListView) findViewById(com.studio.mpak.orshankanews.R.id.list);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        listView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(com.studio.mpak.orshankanews.R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                Article article = (Article) listView.getItemAtPosition(position);
//                Uri webPage = Uri.parse(article.getArticleUrl());
//
//                WebViewFragment fragment = new WebViewFragment();
//                Bundle args = new Bundle();
//                args.putString(ArticleEntry.COLUMN_URL, webPage.toString());
//                fragment.setArguments(args);
//                getFragmentManager().beginTransaction().replace(R.id.main, fragment, TAG_FRAGMENT).addToBackStack(null).commit();


                Article article = (Article) listView.getItemAtPosition(position);
                Uri webPage = Uri.parse(article.getArticleUrl());
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(ArticleEntry.COLUMN_URL, webPage.toString());
                startActivity(intent);
                mAdapter.setNotifyOnChange(false);
            }

        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                mAdapter.setNotifyOnChange(true);
                uri = "http://www.orshanka.by/?m=201708&paged=" + page;
                getLoaderManager().restartLoader(CONTENT_LOADER_ID, null, MainActivity.this);
                page++;
            }
        });
*/


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }




}
