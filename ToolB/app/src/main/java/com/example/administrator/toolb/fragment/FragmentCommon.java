package com.example.administrator.toolb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.toolb.R;
import com.example.administrator.toolb.activity.ShowActivity;
import com.example.administrator.toolb.adapter.RecycleAdapter;
import com.example.administrator.toolb.db.NewsDbManager;
import com.example.administrator.toolb.entity.News;
import com.example.administrator.toolb.utils.SpfManager;
import com.example.administrator.toolb.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 这是共用的fragment 复用
 * 这里主要就是获取数据  将数据载入布局里边  外边只需要传入不同的网络地址  url 即可
 * Created by Administrator on 2016/7/16.
 */
public class FragmentCommon extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private RecycleAdapter adapter;
    private ArrayList<News> list;
    private String url;
    SwipeRefreshLayout sr;
    boolean isloading,loadingfinish;
    ArrayList<News> all;
    private NewsDbManager newsDbManager;
    SpfManager spfManager;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_common,null);
        newsDbManager=new NewsDbManager(getContext());
        spfManager=new SpfManager(getActivity());
        isloading=false;
        loadingfinish=true;
        //初始化recycleView
        recyclerView= (RecyclerView) view.findViewById(R.id.recycleView);
        //设置recycleView的显示的方式 有三种 1.线性 2.网格 3.瀑布
        layoutManager=new LinearLayoutManager(getContext());
        sr= (SwipeRefreshLayout)view.findViewById(R.id.sr);
        sr.setColorSchemeResources(R.color.color_one,R.color.color_two,R.color.color_three,R.color.color_four);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //更新
//                getData();
                //关闭
                sr.setRefreshing(false);
            }
        });
        list=new ArrayList<>();
        all=new ArrayList<>();

        getData();
//        all = newsDbManager.findAll();
//        Log.e("wo", "onCreateView: "+all.size() );
//        //加载数据了
////        getData();
//        if (spfManager.getFirst()||(all.size()<=0)){
//            Log.e("wo", "哈啊哈 " );
//            getData();
//        }
//        else {
//            all = newsDbManager.findAll();
//            Log.e("wo", "onCreateView12121: "+all.size() );
//            adapter=new RecycleAdapter(all,getContext());
//            recyclerView.setLayoutManager(layoutManager);
//            adapter.setRecycleViewClickListener(new RecycleAdapter.RecycleViewClickListener() {
//                @Override
//                public void OnItemClick(View view, int position) {
//                    Toast.makeText(getContext(), "你猜！！！", Toast.LENGTH_SHORT).show();
////                    Intent intent=new Intent(getContext(),ShowActivity.class);
////                    String url = list.get(position).getUrl();
////                    String title=list.get(position).getTitle();
////                    intent.putExtra("key",url);
////                    intent.putExtra("msg",title);
////                    startActivity(intent);
//                }
//
//                @Override
//                public void OnItemLongClick(View view, int position) {
//
//                }
//            });
//            recyclerView.setAdapter(adapter);
//        }
        return view;
    }

    /**
     * 从网络获取数据了
     * 解析获取下来的s 数据  注意解析的时候  标识要一一对应
     * 最后将stringRequest加入请求队列中 就好了
     */
    private void getData() {
        Log.i("msg", "url的地址为: "+getUrl());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    //这个s就是从网络获取下来的数据了 string类型的  接下来就是解析了
                    public void onResponse(String s) {
                        try {
                            //将网络获取下来的s 转换成object类
                            JSONObject jsonObject=new JSONObject(s);
                            //获取其中"result"这个包裹的string的数据
                            String string=jsonObject.getString("result");
                            //再从"result"这个包裹的string的数据中获取一个我们需要的data的数组
                            //我们要的数据真正的在这里变
                            //将上面的"result"这个包裹的string的数据 也转换成object类
                            JSONObject jsonObject1=new JSONObject(string);
                            //接下来就是获取数组了
                            JSONArray jsonArray=new JSONArray(jsonObject1.getString("data"));
                            //分别解析数据了
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //从数组中获取一个个具体的object
                                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                String title=jsonObject2.getString("title");
                                String date=jsonObject2.getString("date");
                                String author_name=jsonObject2.getString("author_name");
                                String thumbnail_pic_s=jsonObject2.getString("thumbnail_pic_s");
                                String url=jsonObject2.getString("url");
                                //这里获取了url但是我不想显示 即可传入null
                                News news=new News(title,date,author_name,thumbnail_pic_s,url);
                                newsDbManager.add(news);
                                list.add(news);
                            }
                            all=newsDbManager.findAll();
                            //加载完数据后 可以发一个消息
                            handler.sendEmptyMessage(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "连接超时！", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getVolleySingleton(getContext()).addToRequestQueue(stringRequest);
    }

    //申明一个handler
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter=new RecycleAdapter(list,getContext());
                    adapter.changeState(0);
                    isloading=false;
                    //给adapter设置点击事件
                    //这就是adapter里边提供的一个监听  传说中的回调
                    adapter.setRecycleViewClickListener(new RecycleAdapter.RecycleViewClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
//                            Toast.makeText(getContext(), "点我，哈哈！", Toast.LENGTH_SHORT).show();
                            //实现的效果是点击跳转
                            Intent intent=new Intent(getContext(),ShowActivity.class);
                            String url = list.get(position).getUrl();
                            String title=list.get(position).getTitle();
                            intent.putExtra("key",url);
                            intent.putExtra("msg",title);
                            startActivity(intent);
                        }

                        @Override
                        public void OnItemLongClick(View view, int position) {
                            Toast.makeText(getContext(), "长按，嘻嘻！", Toast.LENGTH_SHORT).show();
                        }
                    });

                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            int layoutitem=layoutManager.findLastVisibleItemPosition();
                            if(layoutitem==adapter.getItemCount()-1){
                                switch (newState){
                                    case 0://滑动结束
                                        if(isloading&&loadingfinish){

                                        }
                                        //数据加载
                                        break;
                                    case 1://开始滑动

                                        break;
                                    case 2://滑倒顶部或者底部
                                        if(!isloading){
                                            adapter.changeState(2);
                                            isloading=true;
                                        }
                                        //准备加载数据
                                        break;
                                }
                            }
                            Log.i("msg",newState+"开启");
                        }
                    });
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };
}
