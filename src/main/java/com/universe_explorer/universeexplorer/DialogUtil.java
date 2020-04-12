package com.universe_explorer.universeexplorer;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class DialogUtil {
    public static void showDialog(final Context ctx , String msg , boolean goHome){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
        if (goHome){

        }
        else {
            builder.setPositiveButton("确定",null);
        }
        builder.create().show();
    }

    public static void showDialog(Context ctx , View view){
        new AlertDialog.Builder(ctx).setView(view).setCancelable(false).
                setPositiveButton("确定",null).create().show();

    }

}
