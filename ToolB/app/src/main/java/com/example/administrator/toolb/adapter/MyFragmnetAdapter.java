package com.example.administrator.toolb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 这个是用来在 第一个fragment里边存放6个fragment
 * 传入fragment 和 title
 * Created by Administrator on 2016/7/16.
 */
public class MyFragmnetAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<String> titleList;
    public MyFragmnetAdapter(FragmentManager fm,ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
        titleList=new ArrayList<>();
    }

    public ArrayList<Fragment> getList() {
        return list;
    }

    public void setList(ArrayList<Fragment> list) {
        this.list = list;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
