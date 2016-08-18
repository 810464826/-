package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.Collect;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/21.
 */
public class SearchAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater layoutInflater;
    public List<Collect> list;

    public SearchAdapter(Context context, List<Collect> list) {
        this.context = context;
        this.list = list;
        list = new ArrayList<>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder vh=null;
        if (view==null){
            view = layoutInflater.inflate(R.layout.fragsearch_item,null);
            vh = new viewHolder();
            vh.title = (TextView) view.findViewById(R.id.search_item_tv);
            view.setTag(vh);
        }else {
            vh = (viewHolder) view.getTag();
        }
        Collect collect = list.get(i);
        vh.title.setText(collect.getTitle());
        return view;
    }

    class viewHolder{
        TextView title;
    }
}
