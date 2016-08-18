package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/16.
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ImageView> list;

    public MyPagerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<ImageView> getList() {
        return list;
    }

    public void setList(ArrayList<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    //初始化 视图viewPager
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    //摧毁视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
