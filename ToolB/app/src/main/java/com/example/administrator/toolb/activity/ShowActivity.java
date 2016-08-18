package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.toolb.R;
import com.example.administrator.toolb.db.CollectDbManager;
import com.example.administrator.toolb.entity.Collect;
import com.example.administrator.toolb.entity.JeColors;
import com.example.administrator.toolb.utils.SpfManager;

public class ShowActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    ImageView imageView,imageView_back;
    CollectDbManager collectDbManager;
    String url=null;
    String title=null;
    private String place=" ";
    private Toolbar toolbar;
    private SpfManager spfManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        spfManager=new SpfManager(this);
        collectDbManager=new CollectDbManager(this);
        webView= (WebView) findViewById(R.id.webView);
        imageView_back= (ImageView) findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        imageView= (ImageView) findViewById(R.id.image);
        if (place.equals(" ")){
            setToolBar();
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra("key");
        title=intent.getStringExtra("msg");
        webView.loadUrl(url);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.star_selected);
                if (!collectDbManager.isExistTitle(title)) {
                    Collect collect=new Collect(title,url);
                    collectDbManager.add(collect);
                    Toast.makeText(ShowActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowActivity.this, "呜呜，该新闻收藏过了！", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
