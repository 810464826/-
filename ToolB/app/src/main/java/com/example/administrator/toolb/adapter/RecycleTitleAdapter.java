package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.toolb.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/21.
 */
public class RecycleTitleAdapter extends RecyclerView.Adapter<RecycleTitleAdapter.MyViewHolder>{
    private ArrayList<String> list;
    Context context;

    public RecycleTitleAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.title_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        holder.tv_title.setText(list.get(position));
        if (recycleTitleListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getLayoutPosition();
                    recycleTitleListener.itemOnClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public interface  RecycleTitleListener{
        void itemOnClick(View view,int position);
    }

    private RecycleTitleListener recycleTitleListener=null;

    public RecycleTitleListener getRecycleTitleListener() {
        return recycleTitleListener;
    }

    public void setRecycleTitleListener(RecycleTitleListener recycleTitleListener) {
        this.recycleTitleListener = recycleTitleListener;
    }

    public void add(int position,String msg){
        list.add(position,msg);
        notifyItemInserted(position);
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }
}
