package com.example.administrator.toolb.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.utils.SpfManager;

public class AboutActivity extends AppCompatActivity {
    private ImageView btn_back;
    Toolbar toolbar;
    String place = " ";
    SpfManager spfManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spfManager = new SpfManager(this);
        if (place.equals(" ")) {
            setToolBar();
        }
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
