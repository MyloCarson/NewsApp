package com.mylocarson.newsapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mylocarson.newsapp.MainActivity;
import com.mylocarson.newsapp.R;
import com.mylocarson.newsapp.database.NewsAppDBHelper;
import com.mylocarson.newsapp.database.NewsContract;
import com.mylocarson.newsapp.fragment.ArticleFragment;
import com.mylocarson.newsapp.models.ArticlesItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 19/02/2018.
 */


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {
    private final ArrayList<ArticlesItem> articleArrayList;
    private final Context context;
    private final AppCompatActivity activity;
    private SQLiteDatabase sqLiteDatabase;

    public NewsRecyclerAdapter(AppCompatActivity activity, Context context, ArrayList<ArticlesItem> articleArrayList) {
        this.articleArrayList = articleArrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NewsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.news_title.setText(articleArrayList.get(position).getTitle());
        holder.news_desc.setText(articleArrayList.get(position).getDescription());
        Picasso.with(context)
                .load(articleArrayList.get(position).getUrlToImage())
                .placeholder(R.mipmap.no_imagee)
                .error(R.mipmap.no_imagee)
                .resize(250, 250)
                .centerCrop()
                .into(holder.news_image);
        setAnimation(holder.itemView);


    }

    @Override
    public int getItemCount() {
        return this.articleArrayList.size();
    }

    private void setAnimation(View view) {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        view.startAnimation(animation);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        sqLiteDatabase.close();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView news_title;
        private final TextView news_desc;
        private final ImageView news_image;
        private final ImageView saveNews;
        private final ImageView shareNews;

        MyViewHolder(View view) {
            super(view);
            news_title = view.findViewById(R.id.news_title);
            news_desc = view.findViewById(R.id.news_desc);
            news_image = view.findViewById(R.id.news_image);
            saveNews = view.findViewById(R.id.saveNews);
            shareNews = view.findViewById(R.id.shareNews);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,articleArrayList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_LONG).show();
//                    ArticleFragment articleFragment = ArticleFragment.newInstance(articleArrayList.get(getAdapterPosition()),"");
                    ArticleFragment articleFragment = new ArticleFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("param1", articleArrayList.get(getAdapterPosition()));
                    args.putString("param2", "");
                    articleFragment.setArguments(args);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit, R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.add(R.id.firstLayout, articleFragment)
                            .addToBackStack(MainActivity.FRAGMENT_TAG)
                            .commit();
                }
            });

            NewsAppDBHelper newsAppDBHelper = new NewsAppDBHelper(view.getContext());

            sqLiteDatabase = newsAppDBHelper.getWritableDatabase();
            final ContentValues contentValues = new ContentValues();
            final Gson gson = new Gson();


            saveNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticlesItem articlesItem = articleArrayList.get(getAdapterPosition());
                    String stringToSave = gson.toJson(articlesItem);
                    contentValues.put(NewsContract.NewsItem.FIRST_COLUMN, stringToSave);

                    long id = sqLiteDatabase.insert(NewsContract.NewsItem.TABLE_NAME, null, contentValues);
                    if (id != 0) {
                        Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show();
                    }


                }
            });

            shareNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticlesItem articlesItem = articleArrayList.get(getAdapterPosition());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "Check out this news article \n " + articlesItem.getTitle() + " \nLink : " + articlesItem.getUrl() +
                            "\nTry the NewsApp  ");
                    intent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(intent, "SEND TO "));
                }
            });
        }
    }
}
