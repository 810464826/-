package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.JeColors;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MyColorAdapter extends RecyclerView.Adapter<MyColorAdapter.MyViewHolder> {

    List<JeColors> list;
    Context context;

    public MyColorAdapter(Context context, List<JeColors> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_color,null);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        JeColors jeColors = list.get(position);
        holder.tv.setText(jeColors.getName());
        holder.tv.setBackgroundColor(Color.parseColor(jeColors.getPlace()));
        if(recyclerClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    recyclerClickListener.OnItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private RecyclerClickListener recyclerClickListener=null;

    public RecyclerClickListener getRecyclerClickListener() {
        return recyclerClickListener;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.color_tv);
        }
    }
    public interface RecyclerClickListener {
        void OnItemClick(View view, int position);
    }
}
