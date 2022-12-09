package com.xinniu.android.qiqueqiao.fragment.edit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description :
 */

public class PhoneEditFragment extends LazyBaseFragment {

    @BindView(R.id.ed_phone)
    ClearEditText edPhone;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.button)
    TextView button;

    public static int EDIT_PHONE_RESULT = 102;

    private Call mCall;
    private BroadcastReceiver mBroadcastReceiver;

    public static PhoneEditFragment newInstance() {
        Bundle args = new Bundle();
        PhoneEditFragment fragment = new PhoneEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_phone_edit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getCode == null) {
                    return;
                }
                int i = intent.getIntExtra("countdown", 0);
                if (i >= YzmHelper.MAX_MINUTE || i <= 0) {
                    getCode.setText("获取验证码");
                    getCode.setClickable(true);
                    return;
                }
                getCode.setClickable(false);
                getCode.setText(i+"s");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        //设置接收广播的类型
        intentFilter.addAction(YzmHelper.ACTION_TYPE_REGISTER);
        //调用Context的registerReceiver（）方法进行动态注册
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.get_code, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                if (TextUtils.isEmpty(edPhone.getText().toString())) {
                    ToastUtils.showCentetToast(getContext(), "手机号不能为空");
                    return;
                }
                if (edPhone.getText().toString().length()!=11) {
                    ToastUtils.showCentetToast(getContext(), "手机号格式不正确");
                    return;
                }
                YzmHelper.getInstance().startCountDown(1, edPhone.getText().toString());
                break;
            case R.id.button:
                if (TextUtils.isEmpty(edPhone.getText().toString())) {
                    ToastUtils.showCentetToast(getContext(), "手机号不能为空");
                    return;
                }
                if (edPhone.getText().toString().length()!=11) {
                    ToastUtils.showCentetToast(getContext(), "手机号格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(edCode.getText().toString())) {
                    ToastUtils.showCentetToast(getContext(), "验证码不能为空");
                    return;
                }
                RequestManager.getInstance().cmsBind(edPhone.getText().toString(), edCode.getText().toString(), new RequestCallback<String>() {
                    @Override
                    public void requestStart(Call call) {
                        mCall = call;
                        showLoadingDialog(0);
                    }

                    @Override
                    public void onSuccess(String s) {
                        ToastUtils.showCentetImgToast(mContext, "修改成功");
                        getActivity().setResult(EDIT_PHONE_RESULT);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {
                        dissMissDialog();
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
