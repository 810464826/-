package com.example.administrator.toolb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.toolb.entity.Collect;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/17.
 */
public class CollectDbManager {
    private Context context;
    private SQLiteDatabase database;
    private CollectDbHelper helper;

    public CollectDbManager(Context context) {
        this.context = context;
        helper=new CollectDbHelper(context);
        database=helper.getReadableDatabase();
    }

    public Collect find(String title1){
        Collect collect=null;
        Cursor cursor = database.rawQuery("select * from collect where title=?", new String[]{title1});
        if (cursor!=null){
            while(cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                collect=new Collect(title,url);
            }
        }
        return collect;
    }

    public void add(Collect collect){
        ContentValues values=new ContentValues();
        values.put("title",collect.getTitle());
        values.put("url",collect.getUrl());
        database.insert("collect",null,values);
    }

    public void delete(Collect collect){
        String title=collect.getTitle();
        database.delete("collect","title=?",new String[]{title});
    }

    /**
     * public boolean isExist(String title){
     String title1=null;
     Cursor cursor = database.rawQuery("select * from titleList where title =?", new String[]{title});
     if (cursor!=null) {
     while (cursor.moveToNext()) {
     title1 = cursor.getString(cursor.getColumnIndex("title"));
     }
     }
     return title.equals(title1);
     }
     */

    public boolean isExistTitle(String title){
        String title1=null;
        Cursor cursor=database.rawQuery("select * from collect where title=?",new String[]{title});
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                title1 = cursor.getString(cursor.getColumnIndex("title"));
            }
        }
        return title.equals(title1);
    }

    public boolean isExist(Collect collect){
        String title=collect.getTitle();
        Cursor cursor=database.rawQuery("select * from collect where title=?",new String[]{title});
        return cursor.moveToNext();
    }

    public ArrayList<Collect> findAll(){
        ArrayList<Collect> list=new ArrayList<>();
        Cursor cursor=database.rawQuery("select * from collect",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                Collect collect=new Collect(title,url);
                list.add(collect);
            }
        }
        return list;
    }
}
