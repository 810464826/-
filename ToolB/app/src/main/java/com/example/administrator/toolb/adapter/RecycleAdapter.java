package com.example.administrator.toolb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.administrator.toolb.R;
import com.example.administrator.toolb.activity.ShowActivity;
import com.example.administrator.toolb.entity.News;
import com.example.administrator.toolb.utils.VolleySingleton;

import java.util.ArrayList;

/**1.创建一个MyViewHolder内部类  将自己定义的视图 初始化  将视图和adapter绑定起来
 * 2.将每个实体类的属性一一对应 的 一起绑定上去
 * 3.可以创建一个 interface 接口  进行回调使用接口
 *
 *
 * Created by Administrator on 2016/7/16.
 */
public class RecycleAdapter extends RecyclerView.Adapter{
    private ArrayList<News> list;
    private Context context;
    public final int ITEM_HEAD=0;
    public final int ITEM_MAIN=1;
    public final int ITEM_FOOT=2;
//    private Bitmap mbitmap;
    public int  load_more_state=0;//加载状态的标签

    public ArrayList<News> getList() {
        return list;
    }

    public void setList(ArrayList<News> list) {
        this.list = list;
    }

    public RecycleAdapter(ArrayList<News> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 这个方法是将视图布局加载进adapter；里边
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_HEAD){
            HeaderViewHolder headerViewHolder=new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header,parent,false));
            return  headerViewHolder;
        }else if(viewType==ITEM_FOOT){
            FootViewHolder footViewHolder=new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.item_foot,parent,false));
            return  footViewHolder;
        }else{
            MainViewHolder mainViewHolder=new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item,parent,false));
            return  mainViewHolder;
        }
    }


    class HeaderViewHolder extends  RecyclerView.ViewHolder{
        TextView head_title;
        ImageView header_image;
        FrameLayout fl_header;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            head_title= (TextView) itemView.findViewById(R.id.head_title);
            header_image= (ImageView) itemView.findViewById(R.id.header_image);
            fl_header = (FrameLayout) itemView.findViewById(R.id.fl_header);
            fl_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "我是头部！", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, ShowActivity.class);
                    News news=list.get(getLayoutPosition());
                    String url=news.getUrl();
                    String title=news.getTitle();
                    intent.putExtra("key",url);
                    intent.putExtra("msg",title);
                    context.startActivity(intent);
                }
            });
        }
    }
    class FootViewHolder extends  RecyclerView.ViewHolder{
        TextView tv;
        public FootViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.foot_tv);
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_news;
        private TextView tv_title,tv_author,tv_date;
        public MainViewHolder(View itemView) {
            super(itemView);
            iv_news= (ImageView) itemView.findViewById(R.id.iv_news);
            tv_author= (TextView) itemView.findViewById(R.id.tv_author);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_date= (TextView) itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewClickListener!=null){
                        recycleViewClickListener.OnItemClick(v,getLayoutPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recycleViewClickListener!=null){
                        recycleViewClickListener.OnItemLongClick(v,getLayoutPosition());
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 这个方法就是绑定数据了
     * @param holder
     * @param position
     * 注意这里加载图片的时候 setBitmap（String url , ImageView image）
     * 这里的传入的是图片的网络地址  后边传入你要给某个ImageView设置图片
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            News news = list.get(position);
            ((HeaderViewHolder) holder).head_title.setText(news.getTitle());
            setBitmap(news.getThumbnail_pic_s(), ((HeaderViewHolder) holder).header_image);
        } else if (holder instanceof FootViewHolder) {
            switch (load_more_state) {
                case 0:
                    ((FootViewHolder) holder).tv.setText("加载更多");
                    break;
                case 1:
                    break;
                case 2:
                    ((FootViewHolder) holder).tv.setText("加载中");
                    break;
            }
        } else if (holder instanceof MainViewHolder) {
            News news = list.get(position);
            ((MainViewHolder) holder).tv_title.setText(news.getTitle());
            ((MainViewHolder) holder).tv_author.setText(news.getAuthor_name());
            ((MainViewHolder) holder).tv_date.setText(news.getDate());
            setBitmap(news.getThumbnail_pic_s(), ((MainViewHolder) holder).iv_news);
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return ITEM_HEAD;
        }else if(position==list.size()){
            return ITEM_FOOT;
        }else{
            return ITEM_MAIN;
        }
    }

    public void changeState(int state){
        load_more_state=state;
        notifyItemChanged(list.size()+1);
    }

    /**
     * 获取图片的方法   基本就是这样固定的方法
     *1.判断地址是否存在
     *2.新建一个imageRequest请求，里边的参数（url,成功的监听，宽，高，Config ,失败的监听）
     *3.将这个请求加入请求队列中
     */
    public void setBitmap(String url, final ImageView imageView){
        Log.i("msg", "url的长度为: "+url);
        if (url.length()>0){
            Log.i("msg", "url的长度为: "+"1111111111111111111111111111111111");
            ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    //成功后将图片给imageView
                    Log.i("msg", "成功的图片为: "+bitmap);
                    imageView.setImageBitmap(bitmap);
//                    mbitmap=bitmap;
//                    Log.e("TAG", "这里的图片: "+mbitmap);
                }
            },0,0, Bitmap.Config.RGB_565,new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //失败后就设置一个默认的机器人图片
                    imageView.setImageResource(R.drawable.ic_launcher);
                }
            });
            VolleySingleton.getVolleySingleton(context).addToRequestQueue(imageRequest);
        }
        //如果网址无效的话 还是设置一个默认的机器人图片
        else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }
    }

    /**
     * 创建一个内部接口 总之就是为了外部 可以很好的实现回调
     *  OnItemClick(View view,int position) 这里传入的参数是 你点击的具体的某一个视图，和它的位置
     */
    public interface RecycleViewClickListener{
        void OnItemClick(View view,int position);
        void OnItemLongClick(View view,int position);
    }

    //申明一个接口RecycleViewClickListener  方便调用 在绑定数据的时候调用吧
    private RecycleViewClickListener recycleViewClickListener=null;

    public RecycleViewClickListener getRecycleViewClickListener() {

        return recycleViewClickListener;
    }

    public void setRecycleViewClickListener(RecycleViewClickListener recycleViewClickListener) {
        this.recycleViewClickListener = recycleViewClickListener;
    }
}
