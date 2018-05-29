package com.mylocarson.newsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mylocarson.newsapp.models.News;

/**
 * Created by user on 21/02/2018.
 */

public class AppPreference {
    private static final String PREF_NAME = "NewsApp";
    private static SharedPreferences preferences;

    private AppPreference() {
    }

    public static void saveNews(Context context, News news, String whatKindOfNews) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(news);

        switch (whatKindOfNews) {
            case Constants.SPORTS:
                editor.putString(Constants.SPORTS, jsonString);
                break;
            case Constants.HEADLINES:
                editor.putString(Constants.HEADLINES, jsonString);
                break;
            case Constants.ENTERTAINMENT:
                editor.putString(Constants.ENTERTAINMENT, jsonString);
                break;
            case Constants.SEARCH:
                editor.putString(Constants.SEARCH, jsonString);
                break;

        }

        editor.apply();


    }

    public static News getNews(Context context, String whatKindOfNews) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String news = "";
        Gson gson = new Gson();

        switch (whatKindOfNews) {
            case Constants.SPORTS:
                news = preferences.getString(Constants.SPORTS, "NULL");
                break;
            case Constants.HEADLINES:
                news = preferences.getString(Constants.HEADLINES, "NULL");
                break;
            case Constants.ENTERTAINMENT:
                news = preferences.getString(Constants.ENTERTAINMENT, "NULL");
                break;
            case Constants.SEARCH:
                news = preferences.getString(Constants.SEARCH, "NULL");
                break;

        }
        return gson.fromJson(news, News.class);
    }


}
