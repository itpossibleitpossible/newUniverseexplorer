package com.universe_explorer.universeexplorer;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.KeyEvent;

import android.widget.Toast;



public class yqfxdzz extends AppCompatActivity {



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yqfxdzz);
        //MediaPlayer mediaPlayer1 = null;
        // mediaPlayer1 = MediaPlayer.create(this, R.raw.bomb);
        // mediaPlayer1.start();
        setContentView(new hua(this));

        //setContentView()跟swing的add()差不多吧，不过这里只能添加一个控件，默认铺满屏幕
    }


    public boolean onKeyDown(int keyCode,KeyEvent event) { //返回键

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
  /*          long t=System.currentTimeMillis();//获取系统时间

            if(t-time<=1000){

                exit(); //如果500毫秒内按下两次返回键则退出游戏

            }else{

                time=t;

                Toast.makeText(getApplicationContext(),"再按一次退出游戏",Toast.LENGTH_SHORT).show();

            }


*/
  //showChoice();
            Intent intent = new Intent(yqfxdzz.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;

        }

        return false;



    }

    public void exit(){

        yqfxdzz.this.finish();

        new Thread(new Runnable(){

            @Override

            public void run() {

                try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

                System.exit(0);

            }

        }).start();

    }



    }




