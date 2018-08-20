package com.example.android.newsapp;

/**
 * Created by kmurali on 13-08-2018.
 */

public class News {

    private String mTitle;

    private String mCategory;

    private String mUrl;

    private String mAuthor;

    private String mDate;


    public News(String title, String author, String url, String date, String category) {

        mTitle = title;

        mCategory = category;

        mUrl = url;

        mAuthor = author;

        mDate = date;

    }


    public String getTitle() {

        return mTitle;

    }


    public String getCategory() {

        return mCategory;

    }


    public String getUrl() {

        return mUrl;

    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }
}
