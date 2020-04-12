package com.universe_explorer.universeexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zgwx extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int a;
    Handler handler;
    private String[] names = {"国际空间站","天宫二号", "中卫一号", "风云一D","风云二B", "风云二C", "风云三A", "风云三C", "希望一号" };
    List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();


    ListView list;
    SimpleAdapter simpleAdapter;

//    不能用produceList的方法原因是在handler里只能用到handler之外的对象而不能用函数，想要用对象时，就不能在方法里生成对象
//    public void produceList(){
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.simple_item, new String[]{"names"}, new int[]{R.id.name});
//        list = (ListView) findViewById(R.id.mylist);
//        list.setAdapter(simpleAdapter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zgwx);

        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("names", names[i]);
            listItems.add(listItem);
//            produceList();
            simpleAdapter = new SimpleAdapter(this, listItems, R.layout.simple_item, new String[]{"names"}, new int[]{R.id.name});
            list = (ListView) findViewById(R.id.mylist);
            list.setAdapter(simpleAdapter);

        }
//        produceList();
        list.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("他的ID是"+id);

        if (id == 0)
        {
            Intent intent = new Intent(zgwx.this, gjkj.class);
            startActivity(intent);
        }

        if (id == 1)
        {
            Intent intent = new Intent(zgwx.this, tgeh.class);
            startActivity(intent);
        }

        if (id == 2)
        {
            Intent intent = new Intent(zgwx.this, zwyh.class);
            startActivity(intent);
        }

        if (id == 3)
        {
            Intent intent = new Intent(zgwx.this, fy1d.class);
            startActivity(intent);
        }

        if (id == 4)
        {
            Intent intent = new Intent(zgwx.this, fy2b.class);
            startActivity(intent);
        }

        if (id == 5)
        {
            Intent intent = new Intent(zgwx.this, fy2c.class);
            startActivity(intent);
        }

        if (id == 6)
        {
            Intent intent = new Intent(zgwx.this, fy3a.class);
            startActivity(intent);
        }

        if (id == 7)
        {
            Intent intent = new Intent(zgwx.this, fy3c.class);
            startActivity(intent);
        }

        if (id == 8)
        {
            Intent intent = new Intent(zgwx.this, xw1h.class);
            startActivity(intent);
        }
    }
}

