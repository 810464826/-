package com.example.administrator.toolb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.toolb.R;

public class LogoActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        imageView= (ImageView) findViewById(R.id.logo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.donghua);
        imageView.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(MainActivity.class);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    public void startActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }
}
