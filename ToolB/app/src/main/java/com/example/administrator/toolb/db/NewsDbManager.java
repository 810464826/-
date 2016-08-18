package com.example.administrator.toolb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.toolb.entity.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/22.
 */
public class NewsDbManager  {
    SQLiteDatabase database;
    NewsDbHelper helper;
    Context context;

    public NewsDbManager(Context context) {
        this.context = context;
        helper=new NewsDbHelper(context);
        database=helper.getWritableDatabase();
    }

    /**新闻类的实体
     * private String title;
     private String date;
     private String author_name;
     private String thumbnail_pic_s;
     private String url;
     * @param
     */
    public void add(News news){
        Log.e("TAGA", "add: "+"434343434343434343434" );
        ContentValues values=new ContentValues();
        values.put("title",news.getTitle());
        Log.e("TAGA", "add: "+"1" );
        values.put("date",news.getDate());
        values.put("author_name",news.getAuthor_name());
        values.put("thumbnail_pic_s",news.getThumbnail_pic_s());
        values.put("url",news.getUrl());
        database.insert("xinwen",null,values);
        Log.e("TAGA", "add: "+values );
    }

    /**新闻类的实体
     * private String title;
     private String date;
     private String author_name;
     private String thumbnail_pic_s;
     private String url;
     * @param
     */
    public ArrayList<News> findAll(){
        ArrayList<News> list=new ArrayList<>();
        Cursor cursor = database.query("xinwen", null,null,null,null, null,null);
            while (cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String author_name=cursor.getString(cursor.getColumnIndex("author_name"));
                String thumbnail_pic_s=cursor.getString(cursor.getColumnIndex("thumbnail_pic_s"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                News news=new News(title,date,author_name,thumbnail_pic_s,url);
                list.add(news);
        }
        return  list;
    }
}
