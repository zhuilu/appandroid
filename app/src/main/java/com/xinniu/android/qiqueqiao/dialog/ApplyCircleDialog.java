package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

/**
 * Created by lzq on 2018/2/5.
 */

public class ApplyCircleDialog extends AppCompatDialog implements View.OnClickListener {
    EditText contentEt;
    TextView updateButton;
    private int circleId;
    private OnApplyListener OnSendListener;
    private Context context;

    public ApplyCircleDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ApplyCircleDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected ApplyCircleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    public void setCircleId(int circleId){
        this.circleId = circleId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_apply_circle);
        contentEt = (EditText) findViewById(R.id.et_message);
        updateButton = (TextView) findViewById(R.id.btn_positive);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_positive) {
            applyCircle();
        }
    }

    public interface OnApplyListener {
        void onApplySuccess();
        void onApplyFalie(int code,String msg);
    }

    public void setOnApplyListener(OnApplyListener onReleaseDialogListener) {
        this.OnSendListener = onReleaseDialogListener;
    }

    private void applyCircle(){
        String remark = contentEt.getText().toString();
        if (TextUtils.isEmpty(remark)){
            ToastUtils.showCentetImgToast(context,"请输入申请理由");
            return;
        }
//        RequestManager.getInstance().applyCircle(circleId, remark, new CommonCallback() {
//            @Override
//            public void onSuccess(ResultDO resultDO) {
//                dismiss();
//                if (OnSendListener != null){
//                    OnSendListener.onApplySuccess();
//                }
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                dismiss();
//                if (OnSendListener != null){
//                    OnSendListener.onApplyFalie(code,msg);
//                }
//            }
//        });
    }
}
