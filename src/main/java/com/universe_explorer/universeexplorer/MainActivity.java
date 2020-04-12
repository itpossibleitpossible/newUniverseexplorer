package com.universe_explorer.universeexplorer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int yourChoice;
    ImageView image;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView)findViewById(R.id.image);
        image.setImageResource(R.mipmap.openmusic);

        final Intent music= new Intent(this,MyService.class);
        startService(music);
        Toast.makeText(MainActivity.this, "点击右上方按钮可关闭音乐",Toast.LENGTH_SHORT);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0)
                {
                    stopService(music);
                    image.setImageResource(R.mipmap.closemusic);
                    flag=1;
                    Toast.makeText(MainActivity.this, "点击右上方按钮可开启音乐",Toast.LENGTH_SHORT);
                }
                else
                {
                    startService(music);
                    image.setImageResource(R.mipmap.openmusic);
                    flag=0;
                }

            }
        });

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            int[] logos1 = new int[]{
                    R.drawable.wdwx,
                    R.drawable.htzs,
                    R.drawable.sjhs,
                    R.drawable.htyx,
                    R.drawable.bz
            };


            private String[] theParent = new String[]{"我的卫星","航天知识","数据换算","航天游戏","帮助"};
            private String[][] theChild = new String[][]{
                    {"在轨卫星概况","在轨卫星实况","姿态观测"},
                    {"航天系统工程","航天控制","光电信息"},
                    {"大地及空间直角坐标系的转换","时间坐标系的转换","大气层数据的转换"},
                    {"航天知识我来答","月球飞行大作战"},
                    {"功能介绍","开发者介绍"}
            };

            @Override
            public int getGroupCount() {
                return theParent.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return theChild[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return theParent[groupPosition];
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return theChild[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            private TextView getTextView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 100);
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                textView.setPadding(36,0,0,0);
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.white));
                return textView;

            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(MainActivity.this);
                logo.setImageResource(logos1[groupPosition]);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                return ll;

            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition).toString());
                textView.setTextColor(getResources().getColor(R.color.white));
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        };
        ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.list);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                int x =  groupPosition;
                String x1 = String.valueOf(x);
                int y = childPosition;
                String y1 = String.valueOf(y);
                String z = x1 + y1;



                if (z.equals("00")){
                    Intent intent = new Intent(MainActivity.this,zgwx.class);
                    startActivity(intent);
                }

                if (z.equals("01")){
                    Intent intent = new Intent(MainActivity.this,zgwxsk.class);
                    startActivity(intent);
                }

                if (z.equals("02")){
                    Intent intent = new Intent(MainActivity.this,ztgc.class);
                    startActivity(intent);
                }

                if (z.equals("10")){
                    Intent intent = new Intent(MainActivity.this,htxtgc.class);
                    startActivity(intent);
                }

                if (z.equals("11")){
                    Intent intent = new Intent(MainActivity.this,htkz.class);
                    startActivity(intent);
                }

                if (z.equals("12")){
                    Intent intent = new Intent(MainActivity.this,gdxx.class);
                    startActivity(intent);
                }

                if (z.equals("20")){
                    showddjkjzjChoiceDialog();
                }

                if (z.equals("21")){
                    Intent intent = new Intent(MainActivity.this,sjzbx.class);
                    startActivity(intent);
                }

                if (z.equals("22")){
                    showbzxzChoiceDialog();
                }

                if (z.equals("30")){
                    showSingleChoiceDialog();
                }

                if (z.equals("31")){
                    Intent intent = new Intent(MainActivity.this,yqfxdzz.class);
                    startActivity(intent);
                }




                return true;


            }

        });


    }
    private void showSingleChoiceDialog() {
        final String[] items = {"科普类", "专业类"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("请选择您感兴趣的类别");
        //
        //    第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items,
                0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            Toast.makeText(MainActivity.this, "你选择了" + items[yourChoice], Toast.LENGTH_SHORT).show();
                            if (yourChoice==0)
                            {
                                Intent intent = new Intent(MainActivity.this,htlyxkpl.class);
                                startActivity(intent);
                            }
                            if (yourChoice==1)
                            {
                                Intent intent = new Intent(MainActivity.this,htlyxzyl.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
        singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        singleChoiceDialog.show();

    }

    private void showbzxzChoiceDialog() {
        final String[] items = {"标准大气", "南京当地修正"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("请选择您需要转换的类型");
        //
        //    第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items,
                0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface
                                                dialog, int which) {
                        yourChoice
                                = which;
                    }
                });

        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            if (yourChoice==0)
                            {
                                Intent intent = new Intent(MainActivity.this,dqcsjbz.class);
                                startActivity(intent);
                            }
                            if (yourChoice==1)
                            {

                                Intent intent = new Intent(MainActivity.this,dqcsjxz.class);
                                startActivity(intent);

                            }
                        }
                    }
                });

        singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        singleChoiceDialog.show();
    }

    private void showddjkjzjChoiceDialog() {
        final String[] items = {"大地坐标系转换为空间直角坐标系", "空间直角坐标系转换为大地坐标系"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("请选择您需要转换的类型");
        //
        //    第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items,
                0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface
                                                dialog, int which) {
                        yourChoice
                                = which;
                    }
                });

        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            if (yourChoice==0)
                            {
                                Intent intent = new Intent(MainActivity.this,ddjkjzj1.class);
                                startActivity(intent);
                            }
                            if (yourChoice==1)
                            {

                                Intent intent = new Intent(MainActivity.this,ddjkjzj2.class);
                                startActivity(intent);

                            }
                        }
                    }
                });

        singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        singleChoiceDialog.show();
    }


}



