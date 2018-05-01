package com.example.hanna.newsapp;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Helper methods related to requesting and receiving articles data.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String AUTHOR_SEPARATOR = " | ";
    private static final String DATE_SEPARATOR = "T";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Article> fetchArticleData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
       List <Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return articles;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Article> articles = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");


            // If there are results in the features array
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentArticle = resultsArray.getJSONObject(i);

                // Extract the values
                String title = currentArticle.optString("webTitle");
                String section = currentArticle.optString("sectionName");
                String urlAdress = currentArticle.optString("webUrl");
                String date = currentArticle.optString("webPublicationDate");

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
                    // The title will be the full string.
                    primaryTitle = title;
                }

                // If the original title string contains time
                // then store the primary date separately from the time in 2 Strings,
                // so they can be displayed in 2 TextView
                String primaryDate;
                String time;

                // Check whether the date string contains the " T " text
                if (date.contains(DATE_SEPARATOR)) {
                    // Split the string into different parts (as an array of Strings)
                    // based on the " T " text.
                    String[] dateString = date.split(Pattern.quote(DATE_SEPARATOR));
                    primaryDate = dateString[0];
                    time = dateString[1];
                    //Extract the first 5 characters of the time string
                    time = time.substring(0, Math.min(time.length(), 5));
                } else {
                    // Otherwise, there is no " T " text in the date string.
                    // Hence, set the time to say "null".
                    time = null;
                    // The title will be the full string.
                    primaryDate = date;
                }
        // return new ArrayList<Article>
                Article article = new Article(primaryTitle, section, urlAdress, primaryDate, time, author);

                // Add the new {@link Article} to the list of articles.
                articles.add(article);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the articles JSON results", e);
        }
        return articles;
    }
}
