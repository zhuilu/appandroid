package com.xinniu.android.qiqueqiao.dialog;

import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/8/29.
 */

public class PushpokeTimeDialog extends DialogFragment implements View.OnClickListener {


    private TextView pokeTv;
    private ImageView finishImg;

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

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pushpoke_time, null);
        pokeTv = (TextView) view.findViewById(R.id.bGoto_poke);
        finishImg = (ImageView) view.findViewById(R.id.bfinishImg);
        pokeTv.setOnClickListener(this);
        finishImg.setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bGoto_poke:
                ComUtils.goToSet(getActivity());
                dismiss();
                break;
            case R.id.bfinishImg:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        long data = Integer.parseInt(dateFormat.format(date)) ;
        UserInfoHelper.getIntance().setPushTimePoke(data);
    }
}
