package com.universe_explorer.universeexplorer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class ztgc extends AppCompatActivity {
    //attitude value
    private float[] attitude = new float[3];
    //announce the view component
    private Button mainexitbutton;
    private TextView textViewX, textViewY, textViewZ;
    private Button gyroSensing;
    //component listener
    private ClickListener myClickListener;
    //Toast
    private Toast toast;
    //functionSelector
    private int methodSelection;
    //service manager
    private Intent intent;
    private ConsoleService mBoundService;
    private MyHandler handler;
    private Message message;
    private Bundle bundle;
    //timerTask to retrieve message from service
    private boolean timerTaskOn;
    private Timer timer;
    //
    private boolean screenIsOn;
    MyBroadcastReceiver myBroadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ztgc);
        //functions that prepare the view
        SetupUI();
        InitVariety();
        RegComponent();
        //some varieties define
        DoBindService();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myBroadcastReceiver);
        mBoundService.DisconnectSocket();
        if (timerTaskOn) {
            CancelTimer();
        }
        DoUnbindService();
        super.onDestroy();
    }

    //setup ui component
    private void SetupUI() {
        //find the relative component
        mainexitbutton = (Button) findViewById(R.id.mainExitButton);
        //show the attitude euler angles
        textViewX = (TextView) findViewById(R.id.valueX);
        textViewY = (TextView) findViewById(R.id.valueY);
        textViewZ = (TextView) findViewById(R.id.valueZ);
        //function select
        gyroSensing = (Button) findViewById(R.id.gyroSensing);
    }

    //initial varieties
    private void InitVariety() {
        //define the listener
        myClickListener = new ClickListener();
        //broadcast receiver
        screenIsOn = true;
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter();
        //
        methodSelection = 0;
        timerTaskOn = false;
        attitude[0] = attitude[1] = attitude[2] = 0.0f;
        handler = new MyHandler();
    }

    //register components
    private void RegComponent() {
        //specify the listener for component
        textViewX.setOnClickListener(myClickListener);
        textViewY.setOnClickListener(myClickListener);
        textViewZ.setOnClickListener(myClickListener);
        mainexitbutton.setOnClickListener(myClickListener);
        gyroSensing.setOnClickListener(myClickListener);
        //intentFilter to receive the screen on/off broadcast
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }


    //check if the ip is legal
    private boolean CheckIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }

    //connection with service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ConsoleService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    //solution to bind service
    private void DoBindService() {
        intent = new Intent(ztgc.this, ConsoleService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //solution to unbind service
    private void DoUnbindService() {
        unbindService(mConnection);
    }


    //display attitude at textView
    private void DisplayAttitude() {
        FlatAttitudeToDisplay();
        textViewX.setText("X : " + attitude[0]);
        textViewY.setText("Y : " + attitude[1]);
        textViewZ.setText("Z : " + attitude[2]);
    }

    //initial timer
    private void InitTimer() {
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 20, 50);
        timerTaskOn = true;
    }

    //cancel timer
    private void CancelTimer() {
        timer.cancel();
        timerTaskOn = false;
    }

    private void FlatAttitudeToDisplay(){
        for (int i = 0; i < 3; i++){
            attitude[i] = (Math.round(attitude[i] * 10.0f)) / 10.0f;
        }
    }

    //broadcast receiver to check if screen is on
    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_ON: {
                    screenIsOn = true;
                    break;
                }
                case Intent.ACTION_SCREEN_OFF: {
                    screenIsOn = false;
                    break;
                }
            }
        }
    }

    //handler to retrieve attitude array from timerTask
    class MyHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            bundle = msg.getData();
            attitude = bundle.getFloatArray("attitudeToDisplay");
            DisplayAttitude();
            super.handleMessage(msg);
        }
    }

    //timerTask to retrieve attitude array from service
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (screenIsOn) {
                attitude = mBoundService.EulerAngleReturn();
                bundle = new Bundle();
                bundle.putFloatArray("attitudeToDisplay", attitude);
                message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    }

    //button click listener
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainExitButton: {
                    Intent intent = new Intent(ztgc.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case  R.id.gyroSensing:{
                    methodSelection = 2;
                    mBoundService.FunctionSelect(methodSelection);
                    if (!timerTaskOn) {
                        InitTimer();
                    }
                    break;
                }
            }
        }
    }

}

