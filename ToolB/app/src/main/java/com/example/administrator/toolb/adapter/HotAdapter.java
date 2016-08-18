package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.News;
import com.example.administrator.toolb.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * 出现 空指针 1. 返回的  0  -->  list.size()
 *             2.卡片布局里的图片文字 要放在一个具体的 线性 或者相对布局里边！！！
 * Created by Administrator on 2016/7/16.
 */
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyViewHolder>{
    ArrayList<News> list;
    Context context;

    public ArrayList<News> getList() {
        return list;
    }

    public void setList(ArrayList<News> list) {
        this.list = list;
    }

    public HotAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.hot_news,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        Log.e("TAG", "视图为: "+myViewHolder);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        News news=list.get(position);
        Log.e("TAG", "绑定的实体为: "+news);
        holder.hot_title.setText(news.getTitle());
        //同理的 获取图片的方法 传入url 和 imagerView
        setBitmap(news.getThumbnail_pic_s(),holder.hot_image);
        if (recycleViewClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getLayoutPosition();
                    recycleViewClickListener.OnItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=holder.getLayoutPosition();
                    recycleViewClickListener.OnItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }

    }

    private void setBitmap(String url, final ImageView imageView) {
        if (url.length()>0){
            ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.RGB_565 , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    imageView.setImageResource(R.drawable.g);
                }
            });
            VolleySingleton.getVolleySingleton(context).addToRequestQueue(imageRequest);
        }
        else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView hot_image;
        private TextView hot_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            hot_image= (ImageView) itemView.findViewById(R.id.hot_image);
            hot_title= (TextView) itemView.findViewById(R.id.hot_title);
        }
    }


    //这里创建一个内部接口  进行回调
    public interface RecycleViewClickListener{
        void OnItemClick(View view,int position);
        void OnItemLongClick(View view,int position);
    }
    private RecycleViewClickListener recycleViewClickListener;

    public RecycleViewClickListener getRecycleViewClickListener() {
        return recycleViewClickListener;
    }

    public void setRecycleViewClickListener(RecycleViewClickListener recycleViewClickListener) {
        this.recycleViewClickListener = recycleViewClickListener;
    }
}
