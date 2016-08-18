package com.example.administrator.toolb.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.activity.AddtActivity;
import com.example.administrator.toolb.adapter.MyFragmnetAdapter;
import com.example.administrator.toolb.db.ListDbManager;
import com.example.administrator.toolb.tool.PinYin;

import java.util.ArrayList;

/**
 * 出现了空指针  发现viewpager的id找错了！！！！
 * Created by Administrator on 2016/7/16.
 */
public class FragmentNews extends Fragment{
    public static final String Before="http://v.juhe.cn/toutiao/index?type=";
    public static final String After="&key=3aecd6c498b96b0f88cead0bbd4c53e6";
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> list;
    private ArrayList<String> urlList;
    private ArrayList<String> titleList;
    private ArrayList<String> title;
    private MyFragmnetAdapter adapter;
    private RelativeLayout relativeLayout;
    //传递过来的集合标题的集合
    private ArrayList<String> newList;
    private Bundle bundle;
    private PinYin pinyin;
    private ListDbManager listDbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_news,null);
        listDbManager=new ListDbManager(getContext());
        ArrayList<String> all = listDbManager.findAll();
        Log.e("TAG", "onCreateView111111111111111111: "+all );
        ArrayList<String> all1 = listDbManager.findAll();
        Log.e("TAG", "onCreateView111111111111111111: "+all1 );

        newList=new ArrayList<>();
        urlList=new ArrayList<>();
        title=new ArrayList<>();
        titleList=new ArrayList<>();
        list=new ArrayList<>();
        pinyin=new PinYin();
        adapter=new MyFragmnetAdapter(getFragmentManager(),list);
        if (getActivity().getIntent().getExtras()!=null){
            bundle = getActivity().getIntent().getExtras();
            //这是标题的集合
            newList= (ArrayList<String>) bundle.getSerializable("key");
            title=newList;
            Log.e("TAG", "接受的list: "+newList.toString() );
            adapter.setTitleList(newList);
            list=new ArrayList<>();
            for (int i = 0; i < newList.size(); i++) {
                Log.e("TAG", "标题的集合11111: "+title );
                //将fragment传进来 并且和urlList 中的ulr一一绑定
                FragmentCommon fragmentCommon=new FragmentCommon();
                String name=newList.get(i);
                String pinYin = pinyin.getPinYin(name);
                fragmentCommon.setUrl(Before+pinYin+After);
                Log.e("TAG", "onCreateView0000000: "+name );
                //将fragment加入list集合中
                list.add(fragmentCommon);
            }
            adapter.setList(list);
        }
        //if(isFrist.equals("no"))
        else {
            //将不同的网址url和标题 存入集合中
            title.add("社会");
            title.add("军事");
            title.add("时尚");
            title.add("头条");
            title.add("国际");
            title.add("体育");
            //注意就将title和url绑定在一起了
            //注意一一对应
            for (int i = 0; i < title.size(); i++) {
                String name=title.get(i);
                String pinYin = pinyin.getPinYin(name);
                urlList.add(Before+pinYin+After);
            }

            for (int i = 0; i < title.size(); i++) {
                //通过循环  将title集合的所有的title 添加到新的titleList集合中
                titleList.add(title.get(i));
                //将fragment传进来 并且和urlList 中的ulr一一绑定
                FragmentCommon fragmentCommon=new FragmentCommon();
                fragmentCommon.setUrl(urlList.get(i));
                //将fragment加入list集合中
                list.add(fragmentCommon);
            }
            adapter.setTitleList(titleList);
        }
        //通过传过来的的标签显示

        //这个空间是用来存放 标题栏的 这是 + 图标的位置
        relativeLayout= (RelativeLayout) view.findViewById(R.id.news_tab_change_rl);
        //给加号 图标设置点击事件
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddtActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        viewPager= (ViewPager) view.findViewById(R.id.news_viewPager);
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        //设置标题下面的横线的颜色
        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
        viewPager.setAdapter(adapter);
        //将tabLayout和viewPager绑定起来
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
