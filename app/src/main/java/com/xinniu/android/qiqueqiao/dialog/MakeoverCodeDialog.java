package com.xinniu.android.qiqueqiao.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/10/11.
 */

@SuppressLint("ValidFragment")
public class MakeoverCodeDialog extends DialogFragment{

    private Context context;
    private String mobile;
    private BroadcastReceiver mBroadcastReceiver;
    private TextView bYzmTv;
    private EditText yzmEt;

    @SuppressLint("ValidFragment")
    public MakeoverCodeDialog(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return super.onCreateView(inflater, container, savedInstanceState);

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (bYzmTv ==null){
                    return;
                }
                int i = intent.getIntExtra("countdown",0);
                if (i >= YzmHelper.MAX_MINUTE || i <= 1){
                    bYzmTv.setText("获取验证码");
                    bYzmTv.setClickable(true);
                    return;
                }
                bYzmTv.setClickable(false);
                bYzmTv.setText(i+"s");
            }
        };
        IntentFilter intentFilter = new IntentFilter();

        //设置接收广播的类型
        intentFilter.addAction(YzmHelper.ACTION_TYPE_CHANGE_PHONE);

        //调用Context的registerReceiver（）方法进行动态注册
        context.registerReceiver(mBroadcastReceiver, intentFilter);
        mobile = UserInfoHelper.getIntance().getUserName();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_make_overcode, null);
        TextView mSureTv = (TextView) view.findViewById(R.id.msureTv);
        bYzmTv = (TextView) view.findViewById(R.id.bgainCodeTv);
        yzmEt = (EditText) view.findViewById(R.id.mYzmEt);
        TextView goToTv = (TextView) view.findViewById(R.id.goToTv);
        ImageView cacelImg = (ImageView) view.findViewById(R.id.bcacelImg);
        bYzmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComUtils.isPhoneheckPass(context,mobile)) {
                    YzmHelper.getInstance().startCountDown(4, mobile);
                }
            }
        });
        cacelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        goToTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(yzmEt.getText().toString())) {
                    makeoverGroup.makeoverGroup(yzmEt.getText().toString(),mobile);
                }else {
                    ToastUtils.showCentetToast(context,"请输入正确的验证码");
                }

            }
        });

        builder.setView(view);
        return builder.create();
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        context.unregisterReceiver(mBroadcastReceiver);
    }

    public interface makeoverGroup{
        void makeoverGroup(String code,String mobile);
    }
    private makeoverGroup makeoverGroup;

    public void setMakeoverGroup(MakeoverCodeDialog.makeoverGroup makeoverGroup) {
        this.makeoverGroup = makeoverGroup;
    }
}
