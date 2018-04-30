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
    private static final String AUTHOR_SEPARATOR = " | ";
    private static final String DATE_SEPARATOR = "T";


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

        String title = currentArticle.getmTitle();
        // If the original title string contains an author
        // then store the primary title separately from the author in 2 Strings,
        // so they can be displayed in 2 TextView
        String primaryTitle;
        String author;

        // Check whether the title string contains the " | " text
        if (title.contains(AUTHOR_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " | " text.
            String[] parts = title.split(Pattern.quote(AUTHOR_SEPARATOR));
            primaryTitle = parts[0];
            author = parts[1];
        } else {
            // Otherwise, there is no " | " text in the title string.
            // Hence, set the author to say "null".
            author = null;
            TextView authorView = listItemView.findViewById(R.id.author);
            authorView.setVisibility(View.GONE);
            // The title will be the full string.
            primaryTitle = title;
        }

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(primaryTitle);

        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText(author);

        String date = currentArticle.getmDate();
        // If the original title string contains time
        // then store the primary date separately from the time in 2 Strings,
        // so they can be displayed in 2 TextView
        String primaryDate;
        String time;

        // Check whether the title string contains the " | " text
        if (date.contains(DATE_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " | " text.
            String[] parts = date.split(Pattern.quote(DATE_SEPARATOR));
            primaryDate = parts[0];
            time = parts[1];
            //Extract the first 5 characters of the time string
            time = time.substring(0, Math.min(time.length(), 5));
        } else {
            // Otherwise, there is no " | " text in the date string.
            // Hence, set the time to say "null".
            time = null;
            TextView timeView = listItemView.findViewById(R.id.time);
            timeView.setVisibility(View.GONE);
            // The title will be the full string.
            primaryDate = date;
        }

        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(time);

        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(primaryDate);

        return listItemView;
    }
}


