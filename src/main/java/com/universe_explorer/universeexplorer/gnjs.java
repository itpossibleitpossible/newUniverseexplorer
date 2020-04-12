package com.universe_explorer.universeexplorer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class gnjs extends AppCompatActivity {
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnjs);
        show = (TextView)findViewById(R.id.show);

        show.setMovementMethod(ScrollingMovementMethod.getInstance());
        //show.movementMethod = ScrollingMovementMethod.getInstance()
    }
}
