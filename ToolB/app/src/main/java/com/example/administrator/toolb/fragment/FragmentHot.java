package com.example.administrator.toolb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.example.administrator.toolb.adapter.HotAdapter;
import com.example.administrator.toolb.db.HotDbManager;
import com.example.administrator.toolb.entity.News;
import com.example.administrator.toolb.utils.SpfManager;
import com.example.administrator.toolb.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/16.
 */
public class FragmentHot extends Fragment{
    RecyclerView recyclerView;
    HotAdapter adapter;
    ArrayList<News> list,all;
    HotDbManager hotDbManager;
    SpfManager spfManager;
    String url="http://v.juhe.cn/toutiao/index?type=yule&key=3aecd6c498b96b0f88cead0bbd4c53e6";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hot,null);
        recyclerView= (RecyclerView) view.findViewById(R.id.hot_recycler);
        spfManager=new SpfManager(getActivity());
        hotDbManager=new HotDbManager(getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        list=new ArrayList<>();
        all=new ArrayList<>();
        all = hotDbManager.findAll();
        //加载数据了
        getData();
//        if (spfManager.getFirst()||(all.size()<=0)){
//            getData();
//        }else {
//            all = hotDbManager.findAll();
//            adapter=new HotAdapter(getContext());
//            adapter.setList(all);
//            adapter.setRecycleViewClickListener(new HotAdapter.RecycleViewClickListener() {
//                @Override
//                public void OnItemClick(View view, int position) {
//                    Toast.makeText(getContext(), "没网哦，亲！", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(getContext(), "呜呜呜呜没网哦，亲！", Toast.LENGTH_SHORT).show();
//                }
//            });
//            recyclerView.setAdapter(adapter);
//        }
        return view;
    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Log.e("TAG", "获取的数据: "+s);
                            JSONObject jsonObject=new JSONObject(s);
                            //获取result的string数据
                            String string=jsonObject.getString("result");
                            //将上边的数据转成object1
                            JSONObject jsonObject1=new JSONObject(string);
                            //从上边的object里获取data 的数组
                            JSONArray jsonArray=new JSONArray(jsonObject1.getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //从data的数组里 获取 具体的object2
                                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                //最后从object2里获取需要的具体的数据
                                String title=jsonObject2.getString("title");
                                String date=jsonObject2.getString("date");
                                String author_name=jsonObject2.getString("author_name");
                                String thumbnail_pic_s=jsonObject2.getString("thumbnail_pic_s");
                                String url=jsonObject2.getString("url");
                                //可以根据显示需要传入数据  不需要显示传null
                                list.add(new News(title,date,author_name,thumbnail_pic_s,url));
                                hotDbManager.add(new News(title,date,author_name,thumbnail_pic_s,url));
                            }
                                all=hotDbManager.findAll();
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

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter=new HotAdapter(getContext());
                    adapter.setList(list);
                    Log.e("TAG", "集合为: "+list);
                    adapter.setRecycleViewClickListener(new HotAdapter.RecycleViewClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            Intent intent=new Intent(getContext(),ShowActivity.class);
                            String url = list.get(position).getUrl();
                            String title=list.get(position).getTitle();
                            intent.putExtra("key",url);
                            intent.putExtra("msg",title);
                            startActivity(intent);
                        }

                        @Override
                        public void OnItemLongClick(View view, int position) {
                            Toast.makeText(getContext(), "长按，嘿嘿嘿嘿！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("TAG", "适配器为: "+adapter);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };
}
