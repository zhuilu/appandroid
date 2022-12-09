package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by lzq on 2018/1/16.
 */

public class ReleaseDialog extends AppCompatDialog implements View.OnClickListener{
    TextView leftButton;
    TextView rightButton;
    private OnReleaseDialogListener onReleaseDialogListener;

    public ReleaseDialog(Context context) {
        super(context);
    }

    public ReleaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected ReleaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_release);
        leftButton = (TextView) findViewById(R.id.btn_negative);
        leftButton.setOnClickListener(this);
        rightButton = (TextView) findViewById(R.id.btn_positive);
        rightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_negative){
            dismiss();
            if (onReleaseDialogListener != null){
                onReleaseDialogListener.onLeftClick();
            }
        }
        if (id == R.id.btn_positive){
            dismiss();
            if (onReleaseDialogListener != null){
                onReleaseDialogListener.onRightClick();
            }
        }
    }
    public interface OnReleaseDialogListener{
        void onLeftClick();
        void onRightClick();
    }
    public void setOnReleaseDialogListener(OnReleaseDialogListener onReleaseDialogListener){
        this.onReleaseDialogListener = onReleaseDialogListener;
    }
}
