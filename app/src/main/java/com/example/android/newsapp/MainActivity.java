package com.example.android.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, LoaderCallbacks<List<News>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private NewsAdapter mAdapter;

    SwipeRefreshLayout swiperef;

    private SharedPreferences preferences;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        swiperef = (SwipeRefreshLayout) findViewById(R.id.swipeandRefresh);

        swiperef.setOnRefreshListener(this);


        mAdapter = new NewsAdapter(this, new ArrayList<News>());


        final ListView newslist = (ListView) findViewById(R.id.list);


        newslist.setAdapter(mAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        preferences.registerOnSharedPreferenceChangeListener(this);


        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News news = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(news.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webIntent);

            }

        });

        ConnectivityManager connMgr = (ConnectivityManager)

                getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {


            getLoaderManager().initLoader(0, null, this).forceLoad();

        } else {

            TextView loadingMessage = (TextView) findViewById(R.id.load_message);

            loadingMessage.setText(R.string.no_internet_connection);

            loadingMessage.setVisibility(View.VISIBLE);

        }

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent settingsIntent = new Intent(this, SettingsActivity.class);

            startActivity(settingsIntent);

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        swiperef.setRefreshing(false);

        if (data != null) {

            mAdapter.setNotifyOnChange(false);

            mAdapter.clear();

            mAdapter.setNotifyOnChange(true);

            mAdapter.addAll(data);

        }

    }


    @Override

    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        String minPages = sharedPrefs.getString(

                getString(R.string.settings_number_of_articles_label),

                getString(R.string.settings_number_of_articles_default));


        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")

                .encodedAuthority("content.guardianapis.com")

                .appendPath("search")

                .appendQueryParameter("order-by", "newest")

                .appendQueryParameter("show-references", "author")

                .appendQueryParameter("show-tags", "contributor")

                .appendQueryParameter("q", "homebirth")

                .appendQueryParameter("page-size", minPages)

                .appendQueryParameter("api-key", getString(R.string.apikey));

        String url = builder.build().toString();


        return new NewsLoader(MainActivity.this, url);


    }


    @Override

    public void onLoaderReset(Loader<List<News>> loader) {

        mAdapter.clear();

    }


    @Override

    public void onRefresh() {

        TextView loadMessage = (TextView) findViewById(R.id.load_message);

        loadMessage.setVisibility(View.GONE);

        getLoaderManager().initLoader(0, null, this).forceLoad();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        if (s.equals(getString(R.string.settings_order_by_key))) {
            getLoaderManager().restartLoader(0, null, this);
        }

    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

        preferences.unregisterOnSharedPreferenceChangeListener(this);

    }
}