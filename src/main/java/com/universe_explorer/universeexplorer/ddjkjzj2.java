package com.universe_explorer.universeexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ddjkjzj2 extends AppCompatActivity {
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    Button compute,reset,exit;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    double d1,d2,d3,d4,d5,d6,d7,d8,d9,d10;


    private void getString()
    {
        s1 = a1.getText().toString();
        s2 = a2.getText().toString();
        s3 = a3.getText().toString();
    }

    private void getValue()
    {
        d1 = Double.valueOf(s1);
        d2 = Double.valueOf(s2);
        d3 = Double.valueOf(s3);
    }

    private boolean complete()
    {
        if (s1.equals("")||s2.equals("")||s3.equals(""))
            return false;
        return true;
    }
/*
    private void gets()
    {
        //java.text.DecimalFormat df=new java.text.DecimalFormat("#.000000");
        //X = Double.parseDouble(df.format(X));
        s4 = String.valueOf(Ld);
        ///Y = Double.parseDouble(df.format(Y));
        s5 = String.valueOf(Lf);
        //Z = Double.parseDouble(df.format(Z));
        s6 = String.valueOf(Lm);
        s7 = String.valueOf(Bd);
        s8 = String.valueOf(Bf);
        s9 = String.valueOf(Bm);
        s10 = String.valueOf(H);


    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddjkjzj2);

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
                    double a=6378136.49;
                    double b=6356755.00;
                    double e =Math.sqrt(a*a-b*b)/a;
                    double c=a*a/b;
                    double X;     //定义横坐标
                    double Y;     //定义纵坐标
                    double Z;     //定义高度
                    double B;
                    double L;

                    double t,m,n,p,k;
                    X=d1;
                    Y=d2;
                    Z=d3;

                    m=Z/Math.sqrt(X*X+Y*Y);
                    double By=Math.atan(m);
                    n=Z/Math.sqrt(X*X+Y*Y);
                    p=c*e*e/Math.sqrt(X*X+Y*Y);
                    k=1+(a*a-b*b)/(b*b);
                    t=m+p*n/Math.sqrt(k+n*n);
                    B=Math.atan(t);
                    double w=Math.sqrt(1-e*e*Math.sin(B)*Math.sin(B));
                    double N=a/w;
                    double H=Z/Math.sin(B)-N*(1-e*e);
                    int i;
                    for(i=1;B-By>0.0000000001|By-B>0.0000000001;i++)
                    {By=B;
                        n=t;
                        t=m+p*n/Math.sqrt(k+n*n);
                        B=Math.atan(t);
                    }
                    w=Math.sqrt(1-e*e*Math.sin(B)*Math.sin(B));
                    N=a/w;
                    if((X>0)&(Y>0))
                        L=Math.atan(Y/X)+Math.PI;
                    if((X<0)&(Y<0))
                        L=Math.atan(Y/X)+Math.PI;
                    if((X>0)&(Y<0))
                        L=2*Math.PI-Math.atan(Y/X);

                    L=Math.atan2(Y, X);



                    H=Math.sqrt(X*X+Y*Y)/Math.cos(B)-N;
                    int Bd,Bf,Ld,Lf;
                    double Bm,Lm;
                    B=180*B/Math.PI;
                    Bd=(int)B;
                    Bf=(int)((B-Bd)*60);
                    Bm=((B-Bd)*60-Bf)*60;
                    L=180*L/Math.PI;
                    Ld=(int)L;
                    Lf=(int)((L-Ld)*60);
                    Lm=((L-Ld)*60-Lf)*60;
                    Bm=(int)Bm;
                    Lm=(int)Lm;

                    int bm,lm;
                    bm=(int)Bm;
                    lm=(int)Lm;

                    java.text.DecimalFormat df=new java.text.DecimalFormat("#.000000");
                    H = Double.parseDouble(df.format(H));


                    s7=String.valueOf(Ld);
                    s8=String.valueOf(Lf);
                    s9=String.valueOf(lm);
                    s4=String.valueOf(Bd);
                    s5=String.valueOf(Bf);
                    s6=String.valueOf(bm);
                    s10=String.valueOf(H);

                    a7.setText(s7);
                    a8.setText(s8);
                    a9.setText(s9);
                    a10.setText(s10);
                    a4.setText(s4);
                    a5.setText(s5);
                    a6.setText(s6);


                }
                else
                    Toast.makeText(ddjkjzj2.this,"请把横坐标、纵坐标或高度填写完整",Toast.LENGTH_SHORT).show();
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
                ddjkjzj2.this.finish();
            }
        });




    }
}
