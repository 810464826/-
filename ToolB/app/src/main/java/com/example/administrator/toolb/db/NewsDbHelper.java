package com.example.administrator.toolb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/22.
 */
public class NewsDbHelper extends SQLiteOpenHelper {
    public NewsDbHelper(Context context) {
        super(context, "newscc.db", null, 3);
    }

    /**新闻类的实体
     * private String title;
     private String date;
     private String author_name;
     private String thumbnail_pic_s;
     private String url;
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table xinwen(title text,date text,author_name text,thumbnail_pic_s text,url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
