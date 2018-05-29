package com.mylocarson.newsapp.api;

import com.mylocarson.newsapp.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 19/02/2018.
 */

public interface NewsService {
    //    @GET("top-headlines?country=ng&apiKey=a895aff9616c4e0e86cb67aa1f22afdf&pageSize=40")
    @GET("top-headlines")
    Call<News> getUsHeadline(@Query("country") String country, @Query("apiKey") String key);

    @GET("everything")
    Call<News> getEverything(@Query("q") String whatToGet, @Query("apiKey") String key);

    @GET("everything")
    Call<News> getEverything(@Query("q") String whatToGet, @Query("apiKey") String key, @Query("language") String language);

    @GET("everything")
    Call<News> getEverything(@Query("q") String whatToGet, @Query("apiKey") String key, @Query("page") int page);

    @GET("everything")
    Call<News> getEverything(@Query("q") String whatToGet, @Query("apiKey") String key, @Query("page") int page, @Query("language") String language);
}
