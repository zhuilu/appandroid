package com.xinniu.android.qiqueqiao.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuchance on 2018/8/29.
 */

public class PushNotifyDialog extends AppCompatDialog implements View.OnClickListener {


    private ImageView pokeTv;
    private ImageView finishImg;
    Context context;

    public PushNotifyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_push_notify);
        pokeTv = (ImageView) findViewById(R.id.img_open);
        finishImg = (ImageView) findViewById(R.id.bfinishImg);
        pokeTv.setOnClickListener(this);
        finishImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_open:
                ComUtils.goToSet(context);
                dismiss();
                break;
            case R.id.bfinishImg:
                dismiss();
                break;
            default:
                break;
        }
    }


}
