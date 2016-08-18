package com.example.administrator.toolb.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.administrator.toolb.R;

public class MyService extends Service {
    MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action=intent.getStringExtra("action");
        if("play".equals(action)){
            if (mediaPlayer==null){
                mediaPlayer=MediaPlayer.create(this, R.raw.choubaguai);
            }
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }
        if("pause".equals(action)){
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
        if ("stop".equals(action)){
            if (mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer=null;
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer=null;
        super.onDestroy();
    }
}
