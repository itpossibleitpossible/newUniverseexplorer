package com.universe_explorer.universeexplorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class htlyxzyl1 extends AppCompatActivity {
    RadioGroup daan;
    Button syt,ckda,xyt;
    int yourchoice=-1;
    RadioButton A,B,C,D;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htlyxzyl1);

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
               Intent intent = new Intent(htlyxzyl1.this,htlyxzyl.class);
               startActivity(intent);
            }
        });

        ckda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourchoice==-1)
                    Toast.makeText(htlyxzyl1.this,"请答题",Toast.LENGTH_SHORT).show();
                else if(yourchoice==B.getId())
                    showturedialog();
                else
                    showfalsedialog();
            }
        });

        xyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(htlyxzyl1.this,htlyxzyl2.class);
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
                .setMessage("正确。被动稳定是指使用自然环境力矩或物理力矩进行稳定，主动稳定是指使用控制系统进行稳定。越主动，稳定精度越高。")
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
                .setMessage("错误,正确答案为B。被动稳定是指使用自然环境力矩或物理力矩进行稳定，主动稳定是指使用控制系统进行稳定。越主动，稳定精度越高。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }


}


