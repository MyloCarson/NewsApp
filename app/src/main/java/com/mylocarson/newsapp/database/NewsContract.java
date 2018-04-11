package com.mylocarson.newsapp.database;

import android.provider.BaseColumns;

/**
 * Created by user on 22/02/2018.
 */

public class NewsContract {

    private NewsContract(){}

    public static class  NewsItem  implements BaseColumns{
        public static final String TABLE_NAME = "saved_news";
        public static final String FIRST_COLUMN = "news_gson";

    }
}
