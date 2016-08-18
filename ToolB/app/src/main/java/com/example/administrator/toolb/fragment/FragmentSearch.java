package com.example.administrator.toolb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.activity.ShowActivity;
import com.example.administrator.toolb.adapter.SearchAdapter;
import com.example.administrator.toolb.db.CollectDbManager;
import com.example.administrator.toolb.entity.Collect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 */
public class FragmentSearch extends Fragment {
    View view;
    EditText search_et;
    ListView listView,collect_lv;
    SearchAdapter adapter;
    //数据库的list
    List<Collect> list;
    //查询后的数据list
    List<Collect> newlist = new ArrayList<>();
    CollectDbManager collectDbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,null);
        initData();
        return view;
    }
    private void initData() {
        search_et = (EditText) view.findViewById(R.id.search_et);
        listView = (ListView) view.findViewById(R.id.search_list);
//        collect_lv= (ListView) view.findViewById(R.id.collect_lv);
        /**为输入添加TextWatcher监听文字的变化*/
        search_et.addTextChangedListener((TextWatcher) new TextWatcher_Enum());

        /**把数据加到Item*/
        collectDbManager = new CollectDbManager(getActivity());
        list = new ArrayList<>();
//        collect_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ArrayList<Collect> all = collectDbManager.findAll();
//                Log.e("msg", "onItemClick: "+all );
//                Toast.makeText(getContext(), "AI", Toast.LENGTH_SHORT).show();
//                Collect collect=all.get(position);
//                String title=collect.getTitle();
//                String url=collect.getUrl();
//                Intent intent = new Intent(getActivity(), ShowActivity.class);
//                intent.putExtra("msg",title);
//                intent.putExtra("key",url);
//                startActivity(intent);
//            }
//
//        });
        listView.setOnItemClickListener(new onclick());
    }

    @Override
    public void onResume() {
        super.onResume();
        /**把数据加到Item*/
        list = collectDbManager.findAll();
        adapter = new SearchAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**当editetext变化时调用的方法，来判断所输入是否包含在所属数据中*/
    private List<Collect> getNewData(String input_info) {
        //遍历list
        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            //如果遍历到的名字包含所输入字符串
            if (collect.getTitle().contains(input_info)) {
                //将遍历到的元素重新组成一个list
                Collect collect1 = new Collect();
                collect1.setTitle(collect.getTitle());
                collect1.setUrl(collect.getUrl());
                newlist.add(collect1);
            }
        }
        return newlist;
    }
    /**button的点击事件*/
    class onclick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Collect collect = newlist.get(position);
            String title = collect.getTitle();
            String url = collect.getUrl();
            /**跳转*/
            Intent intent = new Intent(getActivity(), ShowActivity.class);
            intent.putExtra("msg",title);
            intent.putExtra("key",url);
            startActivity(intent);
        }
    }
    /**TextWatcher接口*/
    class TextWatcher_Enum implements TextWatcher {

        /**文字变化前*/
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        /** 文字变化时*/
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newlist.clear();
            if (search_et.getText() != null) {
                String input_info = search_et.getText().toString();
                newlist = getNewData(input_info);
                adapter = new SearchAdapter(getActivity(), newlist);
                listView.setAdapter(adapter);
            }
        }
        /** 文字变化后*/
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
