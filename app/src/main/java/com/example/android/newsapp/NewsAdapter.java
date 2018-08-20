package com.example.android.newsapp;

/**
 * Created by kmurali on 13-08-2018.
 */

import android.content.Context;

import android.graphics.drawable.GradientDrawable;

import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(

                    R.layout.news_list_item, parent, false);

        }

        News currentnews = getItem(position);


        TextView titleView = (TextView) listItemView.findViewById(R.id.title);

        String formattedtitle = currentnews.getTitle();


        titleView.setText(formattedtitle);

        TextView categoryView = (TextView) listItemView.findViewById(R.id.category);

        String formattedcategory = currentnews.getCategory();

        categoryView.setText(formattedcategory);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        String formattedauthor = currentnews.getAuthor();

        authorView.setText(formattedauthor);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        String formatteddate = currentnews.getDate();

        dateView.setText(formatteddate);

        return listItemView;
    }
}
