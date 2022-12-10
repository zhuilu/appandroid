package com.xinniu.android.qiqueqiao.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.InviteFriendActivity;
import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/9/10.
 */

@SuppressLint("ValidFragment")
public class    NoLinkDialog extends DialogFragment {

    private String nolinkMsg;

    public void setNolinkMsg(String nolinkMsg) {
        this.nolinkMsg = nolinkMsg;
    }

    @SuppressLint("ValidFragment")
    public NoLinkDialog(String nolinkMsg) {
        this.nolinkMsg = nolinkMsg;
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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_nolink, null);
        TextView mnolinkmsg = (TextView) view.findViewById(R.id.mnolinkmsg);
        TextView bgobugVip = (TextView) view.findViewById(R.id.bgobugVip);
        TextView bgotoTask = (TextView) view.findViewById(R.id.bgocomTask);
        ImageView finishImg = (ImageView) view.findViewById(R.id.bfinish_img);
        mnolinkmsg.setText(nolinkMsg);
        bgobugVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VipV4ListActivity.class));
                dismiss();
            }
        });
        bgotoTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteFriendActivity.start(getActivity());
                dismiss();
            }
        });
        finishImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

}
