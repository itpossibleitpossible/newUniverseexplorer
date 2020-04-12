package com.universe_explorer.universeexplorer;

import android.app.Service;

import android.content.Intent;

import android.media.MediaPlayer;

import android.os.IBinder;

import android.support.annotation.Nullable;

import android.util.Log;



public class MyService extends Service {

    MediaPlayer mediaPlayer;



    //必须要实现此方法，IBinder对象用于交换数据，此处暂时不实现

    @Nullable

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }



    @Override

    public void onCreate() {

        super.onCreate();

        mediaPlayer =MediaPlayer.create(this,R.raw.backgroundmusic);

        Log.e("TAG","create");



    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        play();

        Log.e("TAG","start");

        return super.onStartCommand(intent, flags, startId);

    }



    //封装播放

    private void play() {

        mediaPlayer.start();

    }



    //service被关闭之前调用

    @Override

    public void onDestroy() {

        super.onDestroy();

        mediaPlayer.stop();

        Log.e("TAG","destoryed");

    }

}
