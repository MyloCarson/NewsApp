package com.mylocarson.newsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class SearchFragment extends Fragment {
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

    RecyclerView recyclerView;
    ProgressBar progressBar;
    AppCompatActivity appCompatActivity;

    public Button readMoreButton;
    public ProgressBar readMoreProgress;
    EditText searchText;

    public int page_count = 1;

    NewsRecyclerAdapter newsRecyclerAdapter;

    private String whatToNewsToSearch = "";



    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.searchRecycler);
        readMoreButton = (Button)view.findViewById(R.id.readMore);
        readMoreProgress = (ProgressBar)view.findViewById(R.id.readMoreProgress);
        appCompatActivity = (AppCompatActivity)getActivity();
        searchText =  (EditText)view.findViewById(R.id.searchText);

        newsService = new NewsAPI().getNewsService();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                doSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                    Log.e("Editable",editable.toString());
                    if (!editable.toString().isEmpty()){
                        doSearch(editable.toString());
                    }
            }
        });

        //doSearch("nigeria + president");


        return view;
    }

    private void doSearch(String param){
        whatToNewsToSearch = "nigeria + " + param;
        Call<News> getEverythingSports = newsService.getEverything(param,Constants.API_KEY,Constants.LANGUAGE);
        getEverythingSports.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.d("STATUS", response.body().getStatus());
                Log.d("ConnectStat", response.message());
                Log.d("ConnCode", Integer.toString(response.code()));

                if (response.body() != null) {
                    news = response.body();

                    AppPreference.saveNews(getContext(),news,Constants.SEARCH);//save news in SharedPref for next use

                    try {
                        articlesItemArrayList = response.body().getArticles();
                    } catch (Exception e) {
                        Log.e("Sports","From Sports Fragment "+e.toString());
                        Snackbar.make(recyclerView,"Issues with fetching data",Snackbar.LENGTH_SHORT).show();
                    }

                    setUpRecycler(articlesItemArrayList);

                }else{
                    Snackbar.make(recyclerView,"Issues with fetching data",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Log.e("Error", t.toString());
                Log.e("Error2", t.getMessage());
                Toast.makeText(getContext(), "Check Network and Refresh", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecycler(final ArrayList<ArticlesItem> articlesItemArrayList){
        newsRecyclerAdapter = new NewsRecyclerAdapter(appCompatActivity, getContext(), articlesItemArrayList);
        recyclerView.setAdapter(newsRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

//                    if (articlesItemArrayList.size()>0){
//                        /**
//                         * if the arrayList contains items, then show the readmore button**/
//                        readMoreButton.setVisibility(View.VISIBLE);
//                    }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)){
                    readMoreButton.setVisibility(View.VISIBLE);
                    readMoreButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            readMoreProgress.setVisibility(View.VISIBLE);
                            readMoreButton.setVisibility(View.GONE);

                            page_count = page_count+1;

                            Call<News> getMoreSports = newsService.getEverything(whatToNewsToSearch,Constants.API_KEY,page_count,Constants.LANGUAGE);
                            getMoreSports.enqueue(new Callback<News>() {
                                @Override
                                public void onResponse(Call<News> call, Response<News> response) {
                                    readMoreProgress.setVisibility(View.GONE);
                                    readMoreButton.setVisibility(View.VISIBLE);

                                    if (response.body()!=null){
                                        for (ArticlesItem item: response.body().getArticles()
                                                ) {
                                            articlesItemArrayList.add(item);

                                        }
                                        newsRecyclerAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<News> call, Throwable t) {

                                    page_count = page_count-1;//go back to initial page value

                                    readMoreProgress.setVisibility(View.GONE);
                                    readMoreButton.setVisibility(View.VISIBLE);

                                    Snackbar.make(recyclerView,"Cant recall",Snackbar.LENGTH_LONG).show();

                                }
                            });
                        }
                    });
                }else{
                    readMoreButton.setVisibility(View.GONE);
                }
            }
        });
    }

}
