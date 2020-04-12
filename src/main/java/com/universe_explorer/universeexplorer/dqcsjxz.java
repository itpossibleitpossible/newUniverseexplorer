package com.universe_explorer.universeexplorer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class dqcsjxz extends AppCompatActivity {
    EditText height,speed,size;
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9;
    String sheight,sspeed,ssize;
    String s1,s2,s3,s4,s5,s6,s9;
    double dheight,dspeed,dsize;
    double d1,d2,d3,d4,d5,d6,d9;
    double d11,d22,d33,d44,d55,d66,d99;
    Button compute,reset,exit;

    private void getString()
    {
        sheight = height.getText().toString();
        sspeed = speed.getText().toString();
        ssize = size.getText().toString();
    }

    private void getValue()
    {
        dheight = Double.valueOf(sheight);
        dspeed = Double.valueOf(sspeed);
        dsize = Double.valueOf(ssize);
    }

    private boolean complete()
    {
        if (sheight.equals("") || sspeed.equals("") || ssize.equals(""))
            return false;
        return true;
    }

    private void getd()
    {
        java.text.DecimalFormat df1=new java.text.DecimalFormat("#.0");
        java.text.DecimalFormat df2=new java.text.DecimalFormat("#.00");
        java.text.DecimalFormat df3=new java.text.DecimalFormat("#.0000");//设置保留位数

        d1 = 301.15-dheight/100*0.6;
        d11 = Double.parseDouble(df2.format(d1));

        d2=100100*Math.pow(1-dheight/44300, 5.256);
        d22=Double.parseDouble(df1.format(d2));

        d3=1.293*d2/101325*273.15/d1;//大气密度计算:空气密度=1.293*（实际压力/标准物理大气压）x（273.15/实际绝对温度）
        d33=Double.parseDouble(df3.format(d3));

        d4=d3/1.293;
        d44=Double.parseDouble(df3.format(d4));

        d5=20.0468*Math.sqrt(d1);//音速:a=20.0468*√(T),其中T为温度
        d55=Double.parseDouble(df2.format(d5));

        d6=dspeed/d5;//马赫数计算:M=v/a,其中a为音速，v为速度
        d66=Double.parseDouble(df3.format(d6));


        d9=1.4587/1000000*Math.pow(d1, 1.5)/(d1+110.555);//粘性系数:μ=1.4587*10^-6*T^1.5/(T+110.555),其中T为温度
        d99=d9;

    }

    private void gets()
    {
        s1 = String.valueOf(d11);
        s2 = String.valueOf(d22);
        s3 = String.valueOf(d33);
        s4 = String.valueOf(d44);
        s5 = String.valueOf(d55);
        s6 = String.valueOf(d66);
        s9 = String.valueOf(d99);

    }

    private void seta()
    {
        a1.setText(s1);
        a2.setText(s2);
        a3.setText(s3);
        a4.setText(s4);
        a5.setText(s5);
        a6.setText(s6);
        a9.setText(s9);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dqcsjxz);

        height = (EditText)findViewById(R.id.hegiht);
        speed = (EditText)findViewById(R.id.speed);
        size = (EditText)findViewById(R.id.size);
        a1 = (EditText)findViewById(R.id.a1);
        a2 = (EditText)findViewById(R.id.a2);
        a3 = (EditText)findViewById(R.id.a3);
        a4 = (EditText)findViewById(R.id.a4);
        a5 = (EditText)findViewById(R.id.a5);
        a6 = (EditText)findViewById(R.id.a6);
        a9 = (EditText)findViewById(R.id.a9);
        compute = (Button)findViewById(R.id.compute);
        reset = (Button)findViewById(R.id.reset);
        exit = (Button)findViewById(R.id.exit);

        // sheight = height.getText().toString();
        // dheight =Double.valueOf(sheight);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getString();
                if (complete())
                {
                    getValue();
                    getd();
                    gets();
                    seta();
                }
                else
                    Toast.makeText(dqcsjxz.this,"请输入高度、速度和特征尺寸",Toast.LENGTH_SHORT).show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height.setText("");
                speed.setText("");
                size.setText("");
                a1.setText("");
                a2.setText("");
                a3.setText("");
                a4.setText("");
                a5.setText("");
                a6.setText("");
                a9.setText("");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dqcsjxz.this.finish();
            }
        });






    }
}
