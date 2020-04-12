package com.universe_explorer.universeexplorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class htlyxkpl2 extends AppCompatActivity {
    RadioGroup daan;
    Button syt,ckda,xyt;
    int yourchoice=-1;
    RadioButton A,B,C,D;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htlyxkpl2);

        daan = (RadioGroup) findViewById(R.id.daan);
        syt = (Button)findViewById(R.id.syt);
        ckda = (Button)findViewById(R.id.ckda);
        xyt = (Button)findViewById(R.id.xyt);
        A = (RadioButton)findViewById(R.id.A);
        B = (RadioButton)findViewById(R.id.B);
        C = (RadioButton)findViewById(R.id.C);
        D = (RadioButton)findViewById(R.id.D);

        daan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                yourchoice = checkedId;
            }
        });

        syt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(htlyxkpl2.this,htlyxkpl1.class);
                startActivity(intent);
                finish();
            }
        });

        ckda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourchoice==-1)
                    Toast.makeText(htlyxkpl2.this,"请答题",Toast.LENGTH_SHORT).show();
                else if(yourchoice==D.getId())
                    showturedialog();
                else
                    showfalsedialog();
            }
        });

        xyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(htlyxkpl2.this, htlyxkpl3.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showturedialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("解析")
                .setIcon(R.drawable.dialoglogo)
                .setMessage("正确。舱内航天服是航天员的个人防护系统，当飞船出现意外失压时，可在6小时内保证航天员的生命安全。中国航天人历经10年研制的舱内航天服重约11公斤。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void showfalsedialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("解析")
                .setIcon(R.drawable.dialoglogo)
                .setMessage("错误，正确答案为D。舱内航天服是航天员的个人防护系统，当飞船出现意外失压时，可在6小时内保证航天员的生命安全。中国航天人历经10年研制的舱内航天服重约11公斤。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }


}

