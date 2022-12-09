package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.YzmBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.YzmCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CountDownTextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.MD5;

/**
 * 修改登录密码
 * Created by yuchance on 2018/12/24.
 */

public class ChangeCodeActivity extends BaseActivity {
    @BindView(R.id.mBindPhonenum)
    TextView mBindPhonenum;
    @BindView(R.id.mchangecodeEt)
    EditText mchangecodeEt;
    @BindView(R.id.mchangecodeYzm)
    EditText mchangecodeYzm;
    @BindView(R.id.byzmchangeCode)
    CountDownTextView byzmchangeCode;
    private String phoneNum;


    public static void start(Context context) {
        Intent intent = new Intent(context, ChangeCodeActivity.class);

        context.startActivity(intent);


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_changecode;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        phoneNum = UserInfoHelper.getIntance().getUserName();
        if(!StringUtils.isEmpty(phoneNum)) {//防止异常
            ShowUtils.showTextPerfect(mBindPhonenum, ComUtils.phone2star(phoneNum));
        }
        byzmchangeCode.setCountDownMillis(60000);
        byzmchangeCode.setmHintText("");
        byzmchangeCode.setmCountUnit("S");
        byzmchangeCode.setmReText("重新发送");
        byzmchangeCode.setUsableColorId(R.color.white);

    }


    @OnClick({R.id.byzmchangeCode, R.id.bchangeCodetv, R.id.rlayout_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.byzmchangeCode:
                gainYzm(phoneNum);
                break;
            case R.id.bchangeCodetv:
                String yzm = mchangecodeEt.getText().toString();
                String code = mchangecodeYzm.getText().toString();
                if (yzm.length() != 6) {
                    ToastUtils.showCentetToast(ChangeCodeActivity.this, "验证码错误");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showCentetToast(ChangeCodeActivity.this, "新密码要大于6位");
                    return;
                }
                if (code.length() < 6) {
                    ToastUtils.showCentetToast(ChangeCodeActivity.this, "新密码要大于6位");
                    return;
                }
                goGhangeCode(yzm, code);
                break;
            case R.id.rlayout_back:
                finish();

                break;
            default:
                break;
        }
    }

    private void goGhangeCode(String yzm, String code) {
        RequestManager.getInstance().changeCode(phoneNum, yzm, code, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(ChangeCodeActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ChangeCodeActivity.this, msg);
            }
        });


    }

    private void gainYzm(String phone) {
        String[] signArray = phone.split("");
        Arrays.sort(signArray);
        StringBuffer sign = new StringBuffer();
        for (int i = 0; i < signArray.length; i++) {
            sign.append(signArray[i]);
        }
        sign.append("QiQueqiao2018aySo08pks1k");
        Log.d("===YzmHelper", sign.toString());
        String signx = MD5.encrypt(sign.toString(), true);
        Log.d("===YzmHelper", signx);
        showBookingToast(2);
        RequestManager.getInstance().getYzm(phone, 2, signx, new YzmCallback() {
            @Override
            public void onSuccess(YzmBean msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ChangeCodeActivity.this, msg.msg);
            }

            @Override
            public void onFailed(String error, int code) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ChangeCodeActivity.this, error);
                byzmchangeCode.reset();
            }
        });
    }


}
