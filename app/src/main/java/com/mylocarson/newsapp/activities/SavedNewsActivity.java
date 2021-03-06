package com.mylocarson.newsapp.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mylocarson.newsapp.R;
import com.mylocarson.newsapp.adapters.SavedNewsRecyclerAdapter;
import com.mylocarson.newsapp.database.NewsAppDBHelper;
import com.mylocarson.newsapp.database.NewsContract;
import com.mylocarson.newsapp.interfaces.OnEmptyRecycler;
import com.mylocarson.newsapp.models.ArticlesItem;

import java.util.ArrayList;

import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class SavedNewsActivity extends AppCompatActivity implements OnEmptyRecycler {

    public static int COUNT_OF_ITEMS_IN_SAVED_ARRAYLIST_IN_ADAPTER = 1;
    private static final ArrayList<Integer> articlesID_fromDB = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView textView;
    private NewsAppDBHelper newsAppDBHelper;
    private final ArrayList<ArticlesItem> articlesItemArrayList = new ArrayList<>();

// --Commented out by Inspection START (29/05/2018, 17:44):
//    public static ArrayList<Integer> getArticlesID_fromDB() {
//        return articlesID_fromDB;
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:44)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.savedNewsRecycler);
        textView = findViewById(R.id.noNews);

        newsAppDBHelper = new NewsAppDBHelper(this);

        getNews();
        setRecyclerView();
    }

    private void getNews() {
        SQLiteDatabase sqLiteDatabase = newsAppDBHelper.getReadableDatabase();
        String[] selectionArgs = {NewsContract.NewsItem.FIRST_COLUMN};
        String[] cols = {NewsContract.NewsItem._ID, NewsContract.NewsItem.FIRST_COLUMN};

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + NewsContract.NewsItem.TABLE_NAME, null);
//        Cursor cursor = sqLiteDatabase.query(NewsContract.NewsItem.TABLE_NAME,cols,null,null,null,null);
        final Gson gson = new Gson();
        while (cursor.moveToNext()) {
            @SuppressWarnings("UnusedAssignment") ArticlesItem articlesItem = new ArticlesItem();
            int articleID = cursor.getInt(cursor.getColumnIndexOrThrow(NewsContract.NewsItem._ID));
            String fromString = cursor.getString(cursor.getColumnIndexOrThrow(NewsContract.NewsItem.FIRST_COLUMN));
            articlesItem = gson.fromJson(fromString, ArticlesItem.class);
            Log.e("Arr", articlesItem.toString());
            articlesItem.setArticleId_fromDB(articleID);
            articlesItemArrayList.add(articlesItem);
            articlesID_fromDB.add(articleID);
        }
        if (articlesItemArrayList.size() == 0 || COUNT_OF_ITEMS_IN_SAVED_ARRAYLIST_IN_ADAPTER == 0) {
            textView.setVisibility(View.VISIBLE);
        }

        cursor.close();
    }

    private void setRecyclerView() {
        SavedNewsRecyclerAdapter savedNewsRecyclerAdapter = new SavedNewsRecyclerAdapter(this, this, articlesItemArrayList);
        recyclerView.setAdapter(savedNewsRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

// --Commented out by Inspection START (29/05/2018, 17:55):
//    @Override
//    public void showNoEventTextView() {
//        Toast.makeText(this, "No More News", Toast.LENGTH_SHORT).show();
//        textView.setVisibility(View.VISIBLE);
//    }
// --Commented out by Inspection STOP (29/05/2018, 17:55)
}
