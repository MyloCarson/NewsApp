package com.mylocarson.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by user on 22/02/2018.
 */

public class NewsAppDBHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "newsapp.db";
    private static final int DATABASE_VERSION = 1;


    private static final String CREATE_TABLE_STATEMENT = " CREATE TABLE " + NewsContract.NewsItem.TABLE_NAME + " ( " +
            NewsContract.NewsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NewsContract.NewsItem.FIRST_COLUMN +
            " TEXT NOT NULL );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewsItem.TABLE_NAME;

    public NewsAppDBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
