package com.universe_explorer.universeexplorer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ddjkjzj1 extends AppCompatActivity {
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    Button compute,reset,exit;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    double d1,d2,d3,d4,d5,d6,d7,d8,d9,d10;
    double a=6378136.49;
    double b=6356755.00;
    double e =Math.sqrt(a*a-b*b)/a;
    double c=a*a/b;
    double X,Y,Z,B,L,N;
    String SX,SY,SZ;

    private void getString()
    {
        s1 = a1.getText().toString();
        s2 = a2.getText().toString();
        s3 = a3.getText().toString();
        s4 = a4.getText().toString();
        s5 = a5.getText().toString();
        s6 = a6.getText().toString();
        s7 = a7.getText().toString();
    }

    private void getValue()
    {
        d1 = Double.valueOf(s1);
        d2 = Double.valueOf(s2);
        d3 = Double.valueOf(s3);
        d4 = Double.valueOf(s4);
        d5 = Double.valueOf(s5);
        d6 = Double.valueOf(s6);
        d7 = Double.valueOf(s7);
    }

    private boolean complete()
    {
        if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals("")||s7.equals(""))
            return false;
        return true;
    }

    private void gets()
    {
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.000000");
        X = Double.parseDouble(df.format(X));
        SX = String.valueOf(X);
        Y = Double.parseDouble(df.format(Y));
        SY = String.valueOf(Y);
        Z = Double.parseDouble(df.format(Z));
        SZ = String.valueOf(Z);
    }

    private void seta()
    {
        a8.setText(SX);
        a9.setText(SY);
        a10.setText(SZ);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddjkjzj1);

        a1 = (EditText)findViewById(R.id.a1);
        a2 = (EditText)findViewById(R.id.a2);
        a3 = (EditText)findViewById(R.id.a3);
        a4 = (EditText)findViewById(R.id.a4);
        a5 = (EditText)findViewById(R.id.a5);
        a6 = (EditText)findViewById(R.id.a6);
        a7 = (EditText)findViewById(R.id.a7);
        a8 = (EditText)findViewById(R.id.a8);
        a9 = (EditText)findViewById(R.id.a9);
        a10 = (EditText)findViewById(R.id.a10);

        compute = (Button)findViewById(R.id.compute);
        reset = (Button)findViewById(R.id.reset);
        exit = (Button)findViewById(R.id.exit);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getString();
                if (complete())
                {
                    getValue();
                    B=Math.PI*(d1+d2/60+d3/3600)/180;
                    L=Math.PI*(d4+d5/60+d6/3600)/180;
                    N=a/Math.sqrt(1-e*e*Math.sin(B)*Math.sin(B));
                    X=(N+d7)*Math.cos(B)*Math.cos(L);
                    Y=(N+d7)*Math.cos(B)*Math.sin(L);
                    Z=(N*(1-e*e)+d7)*Math.sin(B);
                    gets();
                    seta();
                }
                else
                    Toast.makeText(ddjkjzj1.this,"请把大地纬度、大地经度或高度填写完整",Toast.LENGTH_SHORT).show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a1.setText("");
                a2.setText("");
                a3.setText("");
                a4.setText("");
                a5.setText("");
                a6.setText("");
                a7.setText("");
                a8.setText("");
                a9.setText("");
                a10.setText("");


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ddjkjzj1.this.finish();
            }
        });




    }
}

