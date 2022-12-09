package com.xinniu.android.qiqueqiao.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuchance on 2018/9/25.
 */

@SuppressLint("ValidFragment")
public class ActivityDialog extends DialogFragment implements View.OnClickListener {

    private String imgPath;
    private String jumpUrl;
    private int actId;

    @SuppressLint("ValidFragment")
    public ActivityDialog(String imgPath,int actId, String jumpUrl) {
        this.imgPath = imgPath;
        this.jumpUrl = jumpUrl;
        this.actId = actId;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_activity, null);
        ImageView dialogImg = (ImageView) view.findViewById(R.id.dialog_img);
        ImageView finishImg = (ImageView) view.findViewById(R.id.bfinishImg);
        ImageLoader.loadActImage(imgPath,dialogImg);
//        getDialog().getWindow().setWindowAnimations();
        dialogImg.setOnClickListener(this);
        finishImg.setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        long data = Integer.parseInt(dateFormat.format(date));
        Log.d("==MainActivity", "abcTime:" + data);
        UserInfoHelper.getIntance().setActTime(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        int weeks = calendar.get(Calendar.WEEK_OF_YEAR);
        UserInfoHelper.getIntance().setActTimeWeek(weeks);
        UserInfoHelper.getIntance().setActId(actId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_img:
                if (!jumpUrl.startsWith("http")){
                    jumpUrl = "http://" + jumpUrl;
                }
                ApproveCardActivity.start(getActivity(),"url",jumpUrl,"活动");
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
