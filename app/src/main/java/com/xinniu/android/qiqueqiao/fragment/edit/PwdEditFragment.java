package com.xinniu.android.qiqueqiao.fragment.edit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.PwdEditText;

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

public class PwdEditFragment extends LazyBaseFragment {

    @BindView(R.id.et_pwd_one)
    EditText etPwdOne;
    @BindView(R.id.et_pwd_two)
    PwdEditText etPwdTwo;

    private Call mCall;

    public static PwdEditFragment newInstance() {
        Bundle args = new Bundle();
        PwdEditFragment fragment = new PwdEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pwd_edit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etPwdOne.getText().toString())) {
            ToastUtils.showCentetImgToast(mContext,"旧密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(etPwdTwo.getText().toString())) {
            ToastUtils.showCentetImgToast(mContext,"新密码不能为空");
            return;
        }
        RequestManager.getInstance().pwd(etPwdOne.getText().toString(), etPwdTwo.getText().toString(), new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showLoadingDialog(0);
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(mContext, "修改成功");
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
