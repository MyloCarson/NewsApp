package com.mylocarson.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mylocarson.newsapp.R;
import com.mylocarson.newsapp.adapters.NewsRecyclerAdapter;
import com.mylocarson.newsapp.api.NewsAPI;
import com.mylocarson.newsapp.api.NewsService;
import com.mylocarson.newsapp.models.ArticlesItem;
import com.mylocarson.newsapp.models.News;
import com.mylocarson.newsapp.util.AppPreference;
import com.mylocarson.newsapp.util.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HeadlineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NewsService newsService;
    News news;
    ArrayList<ArticlesItem> articlesItemArrayList;

    RecyclerView recyclerView2;
    ProgressBar progressBar;
    AppCompatActivity appCompatActivity;

    NewsRecyclerAdapter newsRecyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    public HeadlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeadlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadlineFragment newInstance(String param1, String param2) {
        HeadlineFragment fragment = new HeadlineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline, container, false);

        appCompatActivity = (AppCompatActivity) getActivity();
        recyclerView2 = (RecyclerView)view.findViewById(R.id.recyclerview1);
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.headlineSwipe);

//        progressBar.setVisibility(View.VISIBLE);

        newsService = new NewsAPI().getNewsService();


        if (AppPreference.getNews(getContext(),Constants.HEADLINES)!=null){
            news = AppPreference.getNews(getContext(),Constants.HEADLINES);
            setUpRecycler(news.getArticles());
        }else{
            fetchNews();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNews();
            }
        });

        return view;
    }

    private void fetchNews(){
        Call<News> newsCall = newsService.getUsHeadline(Constants.COUNTRY_CODE, Constants.API_KEY);
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                progressBar.setVisibility(View.GONE);
                Log.d("STATUS", response.body().getStatus());
                Log.d("ConnectStat", response.message());
                Log.d("ConnCode", Integer.toString(response.code()));
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (response.body() != null) {
                    news = response.body();
                    AppPreference.saveNews(getContext(),news, Constants.HEADLINES);

                    try {
                        articlesItemArrayList = response.body().getArticles();
                    } catch (Exception e) {
                        Log.e("Entertainment","From Entertainment Fragment "+e.toString());
                        Snackbar.make(recyclerView2,"Issues with fetching data",Snackbar.LENGTH_SHORT).show();
                    }
                    setUpRecycler(articlesItemArrayList);
                }else{
                    Snackbar.make(recyclerView2,"Issues with fetching data",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                progressBar.setVisibility(View.GONE);
                Log.e("Error", t.toString());
                Log.e("Error2", t.getMessage());
                Toast.makeText(getContext(), "Check Network and Refresh", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void setUpRecycler(final ArrayList<ArticlesItem> articlesItemArrayList){
        newsRecyclerAdapter = new NewsRecyclerAdapter(appCompatActivity, getContext(), articlesItemArrayList);
        recyclerView2.setAdapter(newsRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(recyclerView2.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
