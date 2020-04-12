package com.universe_explorer.universeexplorer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class zwyh extends AppCompatActivity {
    SurfaceView surfaceView2, surfaceView1;
    SurfaceHolder surfaceHolder2, surfaceHolder1;
    MediaPlayer mediaPlayer2, mediaPlayer1;
    Button toPlay, pause, stop;
    private TextView show;
    private int b=0;

    Intent music;


    //存储数字变化的数组
    String[] i = new String[]{
            "61%","62%","63%","64%","65%","66%","67%","68%","69%","70%","71%","72%","73%","74%","75%","76%","77%","78%","79%","80%","81%","82%","83%","84%","85%","86%","87%","88%","89%","90%","91%","92%","93%","94%","95%","96%","97%","98%","99%",
            "99%","98%","97%","96%","95%","94%","93%","92%","91%","90%","89%","88%","87%","86%","85%","84%","83%","82%","81%","80%","79%","78%","77%","76%","75%","74%","73%","72%","71%","70%","69%","68%","67%","66%","65%","64%","63%","62%","61%","60%",
            "60%","61%"
    };

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x000)
            {

                show = (TextView)findViewById(R.id.show);
                show.setText(i[b%i.length]);
                b++;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwyh);

        music = new Intent(this,MyService.class);

        mediaPlayer2 = new MediaPlayer();
        mediaPlayer1 = new MediaPlayer();
        toPlay = (Button)findViewById(R.id.toPlay);
        pause = (Button)findViewById(R.id.pause);
        stop = (Button)findViewById(R.id.stop);

        surfaceView2 = (SurfaceView) findViewById(R.id.SurfaceView2);
        surfaceView1 = (SurfaceView) findViewById(R.id.SurfaceView1);
        surfaceHolder2 = surfaceView2.getHolder();
        surfaceHolder1 = surfaceView1.getHolder();

        surfaceView2 = (SurfaceView) findViewById(R.id.SurfaceView2);
        surfaceHolder2 = surfaceView2.getHolder();

        surfaceView1 = (SurfaceView) findViewById(R.id.SurfaceView1);
        surfaceHolder1 = surfaceView1.getHolder();

        //每0.5秒发送消息使数字发生变化
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer2.isPlaying()) {
                    handler.sendEmptyMessage(0x000);
                }
            }
        },0,1000);



        toPlay.setOnClickListener(new View.OnClickListener()        {
            public void onClick(View v)        	{
                stopService(music);
                mediaPlayer2.reset();
//                mediaPlayer2=MediaPlayer.create(MainActivity.this, R.raw.satalite);//读取视频
                mediaPlayer2=MediaPlayer.create(zwyh.this, R.raw.zwyh);
                mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer2.setDisplay(surfaceHolder2);//设置屏幕
                // TODO Auto-generated method stub
                try {        			mediaPlayer2.prepare();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		} catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		} catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		}        		mediaPlayer2.start();
                mediaPlayer2.setLooping(true);


                mediaPlayer1.reset();
                mediaPlayer1=MediaPlayer.create(zwyh.this, R.raw.dzwyh);//读取视频
//                mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer1.setDisplay(surfaceHolder1);//设置屏幕
                // TODO Auto-generated method stub
                try {        			mediaPlayer1.prepare();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		} catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		} catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();        		}        		mediaPlayer1.start();
                mediaPlayer1.setLooping(true);


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer2.isPlaying()){
                    mediaPlayer2.pause();
                    mediaPlayer1.pause();
                    pause.setText("继续播放");
                }else{
                    mediaPlayer2.start();
                    mediaPlayer1.start();
                    pause.setText("暂停");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2.stop();
                mediaPlayer1.stop();
            }
        });

        if (zwyh.this.isFinishing())
        {
            mediaPlayer2.stop();
            mediaPlayer1.stop();

        }




    }


    public void onStop()
    {
        super.onStop();
        mediaPlayer2.stop();
        mediaPlayer1.stop();
        startService(music);
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(music);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(music);
    }
}
