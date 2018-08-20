package com.example.android.newsapp;

/**
 * Created by kmurali on 13-08-2018.
 */


import android.content.AsyncTaskLoader;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getName();


    private String mUrl;


    public NewsLoader(Context context, String url) {

        super(context);

        mUrl = url;

    }


    @Override

    protected void onStartLoading() {

        super.onStartLoading();

        forceLoad();

    }


    @Override

    public List<News> loadInBackground() {

        List<News> news = null;
        try {
            URL url = QueryUtils.createUrl();
            String jsonparseInfo = QueryUtils.makeHttpRequest(url);
            news = QueryUtils.parseJsonInfo(jsonparseInfo);
        } catch (IOException e) {
            Log.e("Queryutils", "Error in Loader", e);
        }
        return news;

    }
}
