package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.Collect;
import com.example.administrator.toolb.entity.DeleteCollect;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/17.
 */
public class CollectAdapter extends BaseAdapter {
    Context context;
    private boolean isShow;
    ArrayList<DeleteCollect> list;
    public CollectAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
        isShow=false;
    }

    public ArrayList<DeleteCollect> getList() {
        return list;
    }

    public void setList(ArrayList<DeleteCollect> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DeleteCollect getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.collect_item,null);
            holder=new ViewHolder();
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.check_box);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.url= (TextView) convertView.findViewById(R.id.url);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Collect collect=list.get(position).getCollect();
        holder.title.setText(collect.getTitle());
        holder.url.setText(collect.getUrl());


        //给选择框设置一个tag
        //为了让checkbox和list的条目绑定
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(list.get(position).isSelect());
        if (isShow){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.checkBox.setOnCheckedChangeListener(listener);
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView url;
        CheckBox checkBox;
    }

    private CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position=(int)buttonView.getTag();
            getList().get(position).setSelect(isChecked);
        }
    };

    public void change(boolean flag) {
        isShow = flag;
    }
}
