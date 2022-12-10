package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by lzq on 2018/1/18.
 */

public class UpdateDialog extends AppCompatDialog implements View.OnClickListener {
    TextView contentTv;
    TextView updateButton;
    private OnUpdateListener onReleaseDialogListener;

    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    protected UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public UpdateDialog setContent(String str){
        contentTv.setText(str);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        contentTv = (TextView) findViewById(R.id.tv_message);
        updateButton = (TextView) findViewById(R.id.btn_positive);
        updateButton.setOnClickListener(this);
        updateButton.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_positive) {
            if (onReleaseDialogListener != null) {
                onReleaseDialogListener.onUpdateClick();
            }
        }
    }

    public interface OnUpdateListener {
        void onUpdateClick();
    }

    public void setUpdateListener(OnUpdateListener onReleaseDialogListener) {
        this.onReleaseDialogListener = onReleaseDialogListener;
    }
    public void noTouchButton(){
        updateButton.setText("更新中,请等待");
        updateButton.setClickable(false);
        updateButton.setSelected(false);
    }


}
