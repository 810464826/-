package com.example.administrator.toolb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ListDbManager {
    private Context context;
    private ListDbHelper helper;
    private SQLiteDatabase database;

    public ListDbManager(Context context) {
        this.context = context;
        helper=new ListDbHelper(context);
        database=helper.getWritableDatabase();
    }

//    public boolean isExist(String title){
//        String title1=null;
//        boolean flag=false;
//        Cursor cursor = database.rawQuery("select * from titleList where title =?", new String[]{title});
//        if (cursor!=null){
//            if (cursor.moveToNext()){
//                title1=cursor.getString(cursor.getColumnIndex("title"));
//                if (title.equals(title1)){
//                    flag=true;
//                }
//            }
//        }
//        return flag;
//    }

    public boolean isExist(String title){
        String title1=null;
        Cursor cursor = database.rawQuery("select * from titleList where title =?", new String[]{title});
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                title1 = cursor.getString(cursor.getColumnIndex("title"));
            }
        }
        return title.equals(title1);
    }

    public void add(String title){
        ContentValues values=new ContentValues();
        values.put("title",title);
        Log.e("TAG1", "add: "+title );
        database.insert("titleList","title=?",values);
    }

    public void deleteAll(){
        database.delete("titleList",null,null);
    }

    public void delete(String title){
        database.delete("titleList","title=?",new String[]{title});
    }

    public ArrayList<String> findAll(){
        ArrayList<String> list=new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from titleList", null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("title"));
                list.add(title);
                Log.e("TAG1", "findAll: "+title );
            }
        }
        Log.e("TAG1", "findAll: "+list.size());
        return list;
    }

//    public ArrayList<String> findTitle(){
//        ArrayList<String> list=new ArrayList<>();
//        Cursor cursor = database.rawQuery("select * from titleList", null);
//        if (cursor!=null){
//            while (cursor.moveToNext()){
//                String title=cursor.getString(cursor.getColumnIndex("title"));
//                list.add(title);
//                Log.e("TAG1", "findAll: "+title );
//            }
//        }
//        Log.e("TAG1", "findAll: "+list.size());
//        return list;
//    }
}
