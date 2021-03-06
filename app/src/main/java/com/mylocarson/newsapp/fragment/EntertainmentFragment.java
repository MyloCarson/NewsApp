package com.mylocarson.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class EntertainmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NewsService newsService;
    private News news;
    private RecyclerView recyclerView;
    private AppCompatActivity appCompatActivity;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ArticlesItem> articlesItemArrayList;
    private int count = 1;
    private Button readMore;
    private ProgressBar readMoreProgressBar;


    public EntertainmentFragment() {
        // Required empty public constructor
    }

// --Commented out by Inspection START (29/05/2018, 17:44):
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment EntertainmentFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static EntertainmentFragment newInstance(String param1, String param2) {
//        EntertainmentFragment fragment = new EntertainmentFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entertainment, container, false);

        recyclerView = view.findViewById(R.id.ent_Recycler);
        readMore = view.findViewById(R.id.readMoreEnt);
        readMoreProgressBar = view.findViewById(R.id.readMoreProgressEnt);
        swipeRefreshLayout = view.findViewById(R.id.entSwipe);

        appCompatActivity = (AppCompatActivity) getActivity();

        newsService = new NewsAPI().getNewsService();

        if (AppPreference.getNews(getContext(), Constants.ENTERTAINMENT) != null) {
            news = AppPreference.getNews(getContext(), Constants.ENTERTAINMENT);
            setUpRecycler(news.getArticles());
        } else {
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

    private void fetchNews() {
        Call<News> getEverythingSports = newsService.getEverything(Constants.GET_EVERYTHING_ENTERTAINMENT, Constants.API_KEY);
        getEverythingSports.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                Log.d("STATUS", Objects.requireNonNull(response.body()).getStatus());
                Log.d("ConnectStat", response.message());
                Log.d("ConnCode", Integer.toString(response.code()));

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                if (response.body() != null) {
                    news = response.body();
                    AppPreference.saveNews(getContext(), news, Constants.ENTERTAINMENT);//save for later use

                    try {
                        articlesItemArrayList = Objects.requireNonNull(response.body()).getArticles();
                    } catch (Exception e) {
                        Log.e("Entertainment", "From Entertainment Fragment " + e.toString());
                        Snackbar.make(recyclerView, "Issues with fetching data", Snackbar.LENGTH_SHORT).show();
                    }
                    setUpRecycler(articlesItemArrayList);

                } else {
                    Snackbar.make(recyclerView, "Issues with fetching data", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Log.e("Error", t.toString());
                Log.e("Error2", t.getMessage());
                Toast.makeText(getContext(), "Check Network and Refresh", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecycler(final ArrayList<ArticlesItem> articlesItemArrayList) {
        newsRecyclerAdapter = new NewsRecyclerAdapter(appCompatActivity, getContext(), articlesItemArrayList);
        recyclerView.setAdapter(newsRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

//        if (articlesItemArrayList.size()>0){
//            /**
//             * if the arrayList contains items, then show the readmore button**/
//            button.setVisibility(View.VISIBLE);
//        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * check if the user has scrolled to the end of the recycler view**/
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    readMore.setVisibility(View.VISIBLE);
                    readMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            readMoreProgressBar.setVisibility(View.VISIBLE);
                            readMore.setVisibility(View.GONE);
                            count = count + 1; //when the user clicks on the button count (i.e page to request) increase by 1*/
                            Call<News> getNew = newsService.getEverything(Constants.GET_EVERYTHING_ENTERTAINMENT, Constants.API_KEY, count);
                            getNew.enqueue(new Callback<News>() {
                                @Override
                                public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                                    readMoreProgressBar.setVisibility(View.GONE);
                                    readMore.setVisibility(View.VISIBLE);
                                    if (response.body() != null) {
                                        /**
                                         * get next page from the api and add it to recycler adapter */
                                        Snackbar.make(recyclerView, "Page " + count, Snackbar.LENGTH_LONG).show();
                                        articlesItemArrayList.addAll(Objects.requireNonNull(response.body()).getArticles());
                                        newsRecyclerAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                                    count = count - 1; //return count (i.e page to request) to its initial value before request failure*/
                                    readMoreProgressBar.setVisibility(View.GONE);
                                    readMore.setVisibility(View.VISIBLE);
                                    Snackbar.make(recyclerView, "Cant recall", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } else {
                    readMore.setVisibility(View.GONE);
                }
            }
        });
    }


}
