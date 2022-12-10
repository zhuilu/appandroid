package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ExchangeTransPhoneActivity extends BaseActivity {
    @BindView(R.id.edit_title)
    ClearEditText editTitle;

    public static void startSimpleEidtForResult(AppCompatActivity context, int requestCode) {
        Intent intent = new Intent(context, ExchangeTransPhoneActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exchange_trans_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    @OnClick({R.id.bt_finish, R.id.tv_save})
    public void onClick(View view) {
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
        }
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(editTitle.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入交易对方手机号");
                    return;
                }

                getGuaranteeUserInfo(editTitle.getText().toString());

                break;
        }
    }

    private void getGuaranteeUserInfo(String s) {
        showBookingToast(2);
        RequestManager.getInstance().getGuaranteeUserInfo(s, new GetOtherUserInfoCallback() {
            @Override
            public void onSuccess(OtherUserInfo bean) {
                dismissBookingToast();
                int userId = UserInfoHelper.getIntance().getUserId();
                if (userId == bean.getUser_id()) {
                    ToastUtils.showCentetToast(ExchangeTransPhoneActivity.this, "不能和自己交易");
                    editTitle.setText("");
                    editTitle.requestFocus();
                } else {
                    Intent intent = new Intent();
                    Gson gson = new Gson();
                    String data = gson.toJson(bean);
                    intent.putExtra("data", data);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ExchangeTransPhoneActivity.this, msg);
                editTitle.setText("");
                editTitle.requestFocus();

            }
        });
    }

    //监听返回键(有软键盘的情况下想直接返回，需要拦截KeyEvent.ACTION_UP事件)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
