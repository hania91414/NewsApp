package com.example.hanna.newsapp;

/**
 * Created by Hanna on 28.04.2018.
 */

public class Article {
    private String mprimaryTitle;
    private String mSection;
    private String mUrlAdress;
    private String mprimaryDate;
    private String mAuthor;
    private String mTime;

    public Article(String primaryTitle, String section, String urlAdress, String primaryDate, String time, String author) {

        mprimaryTitle = primaryTitle;
        mSection = section;
        mUrlAdress = urlAdress;
        mprimaryDate = primaryDate;
        mAuthor = author;
        mTime = time;
    }

    public String getMprimaryTitle() {
        return mprimaryTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrlAdress() {
        return mUrlAdress;
    }

    public String getMprimaryDate() {
        return mprimaryDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
