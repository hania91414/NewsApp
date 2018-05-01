package com.example.hanna.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Hanna on 28.04.2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_item, parent, false);
        }
        // Get the {@link Article} object located at this position in the list
        Article currentArticle = getItem(position);

        TextView sectionView = listItemView.findViewById(R.id.section);
        sectionView.setText(currentArticle.getmSection());

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentArticle.getMprimaryTitle());

        String author = currentArticle.getmAuthor();
        TextView authorView = listItemView.findViewById(R.id.author);
        if (author != null) ;
        authorView.setText(author);

        String time = currentArticle.getmTime();
        TextView timeView = listItemView.findViewById(R.id.time);
        if (time != null) ;
        timeView.setText(time);

        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(currentArticle.getMprimaryDate());

        return listItemView;
    }
}
