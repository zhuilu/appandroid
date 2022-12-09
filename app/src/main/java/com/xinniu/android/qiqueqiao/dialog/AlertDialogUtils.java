package com.xinniu.android.qiqueqiao.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by yuchance on 2018/11/4.
 */

public class AlertDialogUtils {



    public static void AlertDialog(Context context, String title, String leftMsg, String rightMsg, final setOnClick setOnClick)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(leftMsg)){
            builder.setNegativeButton(leftMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setLeftOnClick(dialog);
                }
            });
        }
        if (!TextUtils.isEmpty(rightMsg)){
            builder.setPositiveButton(rightMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setRightOnClick(dialog);
                }
            });
        }
        builder.show();


    }
    public static void AlertDialog(Context context, String title,String content, String leftMsg, String rightMsg, final setOnClick setOnClick)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        if (!TextUtils.isEmpty(leftMsg)){
            builder.setNegativeButton(leftMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setLeftOnClick(dialog);
                }
            });
        }
        if (!TextUtils.isEmpty(rightMsg)){
            builder.setPositiveButton(rightMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setRightOnClick(dialog);
                }
            });
        }
        builder.show();


    }
    public static void AlertDialog(Activity context, String title, String leftMsg, String rightMsg, boolean style, final setOnClick setOnClick)
    {
        if (!style){
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(leftMsg)){
            builder.setNegativeButton(leftMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setLeftOnClick(dialog);
                }
            });
        }
        if (!TextUtils.isEmpty(rightMsg)){
            builder.setPositiveButton(rightMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setOnClick.setRightOnClick(dialog);
                }
            });
        }
        builder.show();

    }
    public interface setOnClick{
        void setLeftOnClick(DialogInterface dialog);
        void setRightOnClick(DialogInterface dialog);
    }
    private  setOnClick setOnClick;

    public void setSetOnClick(AlertDialogUtils.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
