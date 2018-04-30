package com.example.hanna.newsapp;

/**
 * Created by Hanna on 28.04.2018.
 */

public class Article {

        private String mTitle;
    private String mSection;
    private String mUrlAdress;
    private String mDate;

    public Article(String title, String section, String urlAdress, String date) {

        mTitle = title;
        mSection = section;
        mUrlAdress = urlAdress;
        mDate = date;
        }

        /**
         * Return the title of the Article.
         */
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
}
