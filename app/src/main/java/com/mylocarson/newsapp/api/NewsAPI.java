package com.mylocarson.newsapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocarson.newsapp.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 19/02/2018.
 */

public class NewsAPI {
    private NewsService newsService;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.END_POINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public NewsService getNewsService() {
        this.newsService = retrofit.create(NewsService.class);
        return newsService;
    }
}
