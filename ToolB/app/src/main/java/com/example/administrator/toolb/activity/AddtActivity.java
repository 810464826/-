package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.adapter.RecycleTitleAdapter;
import com.example.administrator.toolb.adapter.TagAdapter;
import com.example.administrator.toolb.db.ListDbManager;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.utils.SpfManager;

import java.util.ArrayList;

public class AddtActivity extends AppCompatActivity {
    private ImageView image_back;
    RecyclerView recyclerView;
    private String place = " ";
    ListDbManager listDbManager;
    RecycleTitleAdapter titleAdapter;
    private Toolbar toolbar;
    private TagAdapter adapter;
    private SpfManager spfManager;
    private ArrayList<String> list,title;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addt);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        if (place.equals(" ")) {
            toolbar.setTitleTextColor(android.graphics.Color.parseColor("#ffffff"));
        }
        listDbManager=new ListDbManager(this);
        spfManager=new SpfManager(this);
        titleAdapter=new RecycleTitleAdapter(this);
        title=new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        image_back= (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });

        listView= (ListView) findViewById(R.id.lv);
        adapter=new TagAdapter(this);
        getDate();
        adapter.setList(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = list.get(position);
                if (!listDbManager.isExist(s)){
                    listDbManager.add(s);
                    titleAdapter.add(title.size(),s);
                }
            }
        });

        titleAdapter.setRecycleTitleListener(new RecycleTitleAdapter.RecycleTitleListener() {
            @Override
            public void itemOnClick(View view, int position) {
                String s = title.get(position);
                listDbManager.delete(s);
                titleAdapter.remove(position);
                ArrayList<String> all = listDbManager.findAll();
                titleAdapter.setList(all);
                titleAdapter.notifyDataSetChanged();
            }
        });

        title=listDbManager.findAll();
        titleAdapter.setList(title);
        recyclerView.setAdapter(titleAdapter);
    }

    private void getDate() {
        list=new ArrayList<>();
        list.add("社会");
        list.add("军事");
        list.add("时尚");
        list.add("头条");
        list.add("国际");
        list.add("国内");
    }

    @Override
    protected void onResume() {
        super.onResume();
        JeColors colord = spfManager.getColord();
        place = colord.getPlace();
        if (!place.equals(" ")) {
            toolbar.setBackgroundColor(Color.parseColor(place));
        }
    }

    public void startActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        Bundle bundle=new Bundle();
        ArrayList<String> all = listDbManager.findAll();
        bundle.putSerializable("key",all);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
