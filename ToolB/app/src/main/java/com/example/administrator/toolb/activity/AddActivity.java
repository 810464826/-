package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.db.ListDbManager;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.utils.SpfManager;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView image_back;
    private String place = " ";
    private Toolbar toolbar;
    private SpfManager spfManager;
    private ArrayList<String> list;
    private ListDbManager listDbManager;
    private TextView tv_one, tv_two, tv_three, tv_four, tv_five, tv_six, one, two, three, four, five, six;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        list = new ArrayList<>();
        spfManager = new SpfManager(this);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        if (place.equals(" ")) {
            toolbar.setTitleTextColor(android.graphics.Color.parseColor("#ffffff"));
        }
        listDbManager = new ListDbManager(this);
        image_back = (ImageView) findViewById(R.id.image_back);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        tv_four = (TextView) findViewById(R.id.tv_four);
        tv_five = (TextView) findViewById(R.id.tv_five);
        tv_six = (TextView) findViewById(R.id.tv_six);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_one.getVisibility() == View.VISIBLE) {
                    String title = tv_one.getText().toString();
                    list.add(title);
                    listDbManager.add(title);
                }
                if (tv_two.getVisibility() == View.VISIBLE) {
                    String title = tv_two.getText().toString();
                    list.add(title);
                    listDbManager.add(title);
                }
                if (tv_three.getVisibility() == View.VISIBLE) {
                    String title = tv_three.getText().toString();
                    list.add(title);
                }
                if (tv_four.getVisibility() == View.VISIBLE) {
                    String title = tv_four.getText().toString();
                    list.add(title);
                    listDbManager.add(title);
                }
                if (tv_five.getVisibility() == View.VISIBLE) {
                    String title = tv_five.getText().toString();
                    list.add(title);
                    listDbManager.add(title);
                }
                if (tv_six.getVisibility() == View.VISIBLE) {
                    String title = tv_six.getText().toString();
                    list.add(title);
                    listDbManager.add(title);
                }
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", list);
                Log.e("TAG", "传递的lis、、: " + list);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        ArrayList<String> all = listDbManager.findAll();
        Log.e("TAG", "表里边的全部标题: " + all);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);
        tv_four.setOnClickListener(this);
        tv_five.setOnClickListener(this);
        tv_six.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                tv_one.setVisibility(View.GONE);

                break;
            case R.id.tv_two:
                tv_two.setVisibility(View.GONE);

                break;
            case R.id.tv_three:
                tv_three.setVisibility(View.GONE);

                break;
            case R.id.tv_four:
                tv_four.setVisibility(View.GONE);

                break;
            case R.id.tv_five:
                tv_five.setVisibility(View.GONE);

                break;
            case R.id.tv_six:
                tv_six.setVisibility(View.GONE);

                break;


            case R.id.one:
                tv_one.setVisibility(View.VISIBLE);
                break;
            case R.id.two:
                tv_two.setVisibility(View.VISIBLE);
                break;
            case R.id.three:
                tv_three.setVisibility(View.VISIBLE);
                break;
            case R.id.four:
                tv_four.setVisibility(View.VISIBLE);
                break;
            case R.id.five:
                tv_five.setVisibility(View.VISIBLE);
                break;
            case R.id.six:
                tv_six.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void startActivity(Class<?> c, Bundle bundle) {
        Intent intent = new Intent(this, c);
        bundle.putSerializable("key", list);
        intent.putExtras(bundle);
        startActivity(intent);
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