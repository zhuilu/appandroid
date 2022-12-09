package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.DialogCompanyAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/12/21.
 */

public class CallPhoneDialog extends AppCompatDialog implements View.OnClickListener {
    Context context;
    ImageView bfinishImg;
    TextView tv_myphone;
    TextView tv_call;
    private CallPhoneCallback mShareCallback;

    public CallPhoneDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call);
        bfinishImg = (ImageView) findViewById(R.id.bfinishImg);
        bfinishImg.setOnClickListener(this);
        tv_myphone = (TextView) findViewById(R.id.tv_myphone);
        tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(this);
        tv_myphone.setText(UserInfoHelper.getIntance().getUserName());

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_call) {
            if (mShareCallback != null) {
                mShareCallback.onClickCall();
            }
            dismiss();
        }
        if (id == R.id.bfinishImg) {
            dismiss();
        }
    }

    public interface CallPhoneCallback {
        void onClickCall();
    }

    public void setmShareCallback(CallPhoneCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }
}
