package com.universe_explorer.universeexplorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sjzbx extends AppCompatActivity {
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    Button compute,reset,exit;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    String time;

    private void getString()
    {
        s1 = a1.getText().toString();
        s2 = a2.getText().toString();
        s3 = a3.getText().toString();
        s4 = a4.getText().toString();
        s5 = a5.getText().toString();
        s6 = a6.getText().toString();
    }

    private boolean complete()
    {
        if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals(""))
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjzbx);

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
                    time=s1+"-"+s2+"-"+s3+" "+s4+":"+s5+":"+s6;

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date d7 = null;
                    try { d7 = df.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    d7.setTime(d7.getTime() - 1000*8*60*60);
                    a7.setText(df.format(d7));

                    Date d8 = null;
                    try { d8 = df.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    d8.setTime(d8.getTime() - 1000*8*60*60 + 18*1000);
                    a8.setText(df.format(d8));

                    Date d9 = null;
                    try { d9 = df.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    d9.setTime(d9.getTime() - 1000*8*60*60 + 27*1000);
                    a9.setText(df.format(d9));

                    Date d10 = null;
                    try { d10 = df.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    d10.setTime(d10.getTime() - 1000*8*60*60 + 37*1000);
                    a10.setText(df.format(d10));

                }
                else
                    Toast.makeText(sjzbx.this,"请将Local Time填写完整",Toast.LENGTH_SHORT).show();

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
                sjzbx.this.finish();
            }
        });

    }
}
