package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.adapter.MyPagerAdapter;
import com.example.administrator.toolb.utils.SpfManager;

import java.util.ArrayList;

public class LeadActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btn_in;
    private MyPagerAdapter adapter;
    private ImageView imageView;
    SpfManager spfManager;
    private ArrayList<ImageView> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spfManager=new SpfManager(this);

        if (spfManager.getFirst()){
            setContentView(R.layout.activity_lead);
            btn_in= (Button) findViewById(R.id.btn_in);
            btn_in.setVisibility(View.GONE);
            initData();
            initViewPager();
        }
        else {
            startActivity(LogoActivity.class);
        }
    }

    private void initData() {
        list=new ArrayList<>();
        imageView= (ImageView) View.inflate(this,R.layout.lead_item1,null);
        list.add(imageView);
        imageView= (ImageView) View.inflate(this,R.layout.lead_item2,null);
        list.add(imageView);
        imageView= (ImageView) View.inflate(this,R.layout.lead_item3,null);
        list.add(imageView);
    }

    private void initViewPager() {
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        adapter=new MyPagerAdapter(this);
        adapter.setList(list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btn_in.setVisibility(View.GONE);
                    if (position==2){
                        btn_in.setVisibility(View.VISIBLE);
                        btn_in.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                spfManager.saveFirst();
                                startActivity(LogoActivity.class);
                            }
                        });
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void startActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }
}
