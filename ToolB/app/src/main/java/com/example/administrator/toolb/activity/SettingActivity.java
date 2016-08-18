package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.adapter.MyColorAdapter;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.service.MyService;
import com.example.administrator.toolb.utils.SpfManager;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btn_back;
    private RecyclerView recyclerView;
    private List<JeColors> list;
    Toolbar toolbar;
    String place = " ";
    private MyColorAdapter adapter;
    private SpfManager spfManager;
    Button btn_start,btn_puase,btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_start= (Button) findViewById(R.id.btn_start);
        btn_puase= (Button) findViewById(R.id.btn_puase);
        btn_stop= (Button) findViewById(R.id.btn_stop);
        toolbar= (Toolbar) findViewById(R.id.toolBar);
        if (place.equals(" ")) {
            toolbar.setTitleTextColor(android.graphics.Color.parseColor("#ffffff"));
        }
        btn_start.setOnClickListener(this);
        btn_puase.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        spfManager=new SpfManager(this);
        btn_back= (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView= (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        initData();
        adapter=new MyColorAdapter(this,list);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerClickListener(new MyColorAdapter.RecyclerClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                spfManager.saveColor(new JeColors(list.get(position).getName(),list.get(position).getPlace()));
                finish();
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new JeColors("粉红色","#FF82AB"));
        list.add(new JeColors("青色","#ADD8E6"));
        list.add(new JeColors("绿色","#66CD00"));
        list.add(new JeColors("紫色","#8E388E"));
        list.add(new JeColors("砖红色","#8B0000"));
        list.add(new JeColors("红色","#EE0000"));
        list.add(new JeColors("黄色","#EEEE00"));
        list.add(new JeColors("灰色","#8B6508"));
        list.add(new JeColors("浅红色","#EE1289"));
        list.add(new JeColors("紫红色","#8B2252"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
            case R.id.btn_start:
                intent.putExtra("action", "play");
                startService(intent);
                break;
            case R.id.btn_puase:
                intent.putExtra("action", "pause");
                startService(intent);
                break;
            case R.id.btn_stop:
                intent.putExtra("action", "stop");
                startService(intent);
                break;
        }
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
