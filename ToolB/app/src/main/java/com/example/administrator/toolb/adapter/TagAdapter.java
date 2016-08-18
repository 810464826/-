package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.toolb.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 */
public class TagAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public TagAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.tag_item,null);
            holder=new ViewHolder();
            holder.tv_tag= (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        String s = list.get(position);
        holder.tv_tag.setText(s);
        return convertView;
    }

    class ViewHolder {
        TextView tv_tag;
    }
}
