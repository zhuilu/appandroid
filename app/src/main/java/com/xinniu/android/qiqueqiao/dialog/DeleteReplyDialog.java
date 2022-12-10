package com.xinniu.android.qiqueqiao.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by yuchance on 2018/11/6.
 */

@SuppressLint("ValidFragment")
public class DeleteReplyDialog extends DialogFragment {

    private String isleft;
    private String ismiddle;
    private String isright;
    private int type = 0;

    @SuppressLint("ValidFragment")
    public DeleteReplyDialog(String isleft, String ismiddle, String isright) {
        this.isleft = isleft;
        this.ismiddle = ismiddle;
        this.isright = isright;
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
        Dialog builder = new Dialog(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete_reply, null);
        Button mtoptv = (Button) view.findViewById(R.id.mtoptv);
        Button mmiddletv = (Button) view.findViewById(R.id.mmiddletv);
        Button mbottomtv = (Button) view.findViewById(R.id.mbottomtv);

        if (!TextUtils.isEmpty(isleft)){
            mtoptv.setVisibility(View.VISIBLE);
            mtoptv.setText(isleft);
        }else {
            mtoptv.setVisibility(View.GONE);

        }
        if (!TextUtils.isEmpty(ismiddle)){

            mmiddletv.setVisibility(View.VISIBLE);
            mmiddletv.setText(ismiddle);
        }else {
                  mmiddletv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(isright)){

            mbottomtv.setVisibility(View.VISIBLE);
            mbottomtv.setText(isright);
        }else {

            mbottomtv.setVisibility(View.GONE);
        }
        mtoptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dismiss();
                setOnClick.setOnClickLeft();
            }
        });
        mmiddletv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dismiss();
                setOnClick.setOnClickMiddle();
            }
        });
        mbottomtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                setOnClick.setOnClickRight();
            }
        });
        builder.getWindow().getAttributes().windowAnimations = R.style.anims;
        builder.setContentView(view);
        return builder;
    }




    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void show(FragmentManager supportFragmentManager, String viewphoto) {
    }

    public interface setOnClick{
        void setOnClickLeft();
        void setOnClickMiddle();
        void setOnClickRight();
        void theOnDismiss(int type);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(DeleteReplyDialog.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setOnClick.theOnDismiss(type);
    }
}
