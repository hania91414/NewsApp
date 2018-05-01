package com.example.hanna.newsapp;

/**
 * Created by Hanna on 28.04.2018.
 */

public class Article {
    private String mTitle;
    private String mSection;
    private String mUrlAdress;
    private String mDate;
    private String mAuthor;

    public Article(String title, String section, String urlAdress, String date, String author) {

        mTitle = title;
        mSection = section;
        mUrlAdress = urlAdress;
        mDate = date;
        mAuthor = author;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrlAdress() {
        return mUrlAdress;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
