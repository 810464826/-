package com.example.administrator.toolb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/22.
 */
public class HotDbHelper extends SQLiteOpenHelper {
    public HotDbHelper(Context context) {
        super(context, "hot.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table hot(title text,date text,author_name text,thumbnail_pic_s text,url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
