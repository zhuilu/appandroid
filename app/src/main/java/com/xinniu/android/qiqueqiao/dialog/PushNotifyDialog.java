package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

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
