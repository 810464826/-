package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.adapter.CollectAdapter;
import com.example.administrator.toolb.db.CollectDbManager;
import com.example.administrator.toolb.entity.Collect;
import com.example.administrator.toolb.entity.DeleteCollect;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.utils.SpfManager;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity {
    private ImageView btn_back;
    Button check, delete;
    CollectAdapter adapter;
    Toolbar toolbar;
    ListView lv;
    ArrayList<DeleteCollect> list;
    private ArrayList<Collect> listCollect;
    CollectDbManager collectDbManager;
    String title,url;
    private boolean flag;
    SpfManager spfManager;
    String place=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        spfManager=new SpfManager(this);
        if (place.equals(" ")){
            setToolBar();
        }
        check= (Button) findViewById(R.id.do_check);
        delete= (Button) findViewById(R.id.do_delete);
        btn_back= (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv= (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Collect collect=list.get(position).getCollect();
                title=collect.getTitle();
                url=collect.getUrl();
                startActivity(ShowActivity.class);
            }
        });
        collectDbManager=new CollectDbManager(this);
        adapter=new CollectAdapter(this);
        list=new ArrayList<>();
        initData();
        adapter.setList(list);
        lv.setAdapter(adapter);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.change(flag);
                flag=!flag;
                adapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = adapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        Collect collect = list.remove(i).getCollect();
                        i--;
                        String title = collect.getTitle();
                        String url = collect.getUrl();
                        Collect collect1 = new Collect(title, url);
                        collectDbManager.delete(collect1);
                    }
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void initData() {
        listCollect=new ArrayList<>();
        listCollect=collectDbManager.findAll();
        for (int i = 0; i < listCollect.size(); i++) {

            Collect collect=listCollect.get(i);
            DeleteCollect deleteCollect=new DeleteCollect(collect,false);
            list.add(deleteCollect);
        }
        Log.e("TAG", "list数据集合是否为空: " +list);
    }

    private void startActivity(Class<ShowActivity> showActivityClass) {
        Intent intent=new Intent(this,showActivityClass);
        intent.putExtra("key",url);
        intent.putExtra("msg",title);
        startActivity(intent);
    }

    public void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(android.graphics.Color.parseColor("#ffffff"));
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
}
