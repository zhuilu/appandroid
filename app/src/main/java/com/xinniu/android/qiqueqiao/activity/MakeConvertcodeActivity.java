package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/11/1.
 * 是对兑换码页面
 */

public class MakeConvertcodeActivity extends BaseActivity {
    @BindView(R.id.mcode_et)
    ClearEditText mcodeEt;

    /**
     * 进入页面
     * @param context 进入此页面的Activity
     */
    public static void start(Activity context) {
        Intent intent = new Intent(context, MakeConvertcodeActivity.class);
        context.startActivityForResult(intent, 106);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_convertcode;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ComUtils.addActivity(this);
    }


    @OnClick({R.id.bt_finish, R.id.bsummit_data,R.id.bgoTobuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bsummit_data:
                if (TextUtils.isEmpty(mcodeEt.getText().toString())) {
                    ToastUtils.showCentetToast(MakeConvertcodeActivity.this, "请输入验证码");
                    return;
                }
                summitData();
                break;
            case R.id.bgoTobuy:
                RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(CenterBean centerBean) {
                        CenterBean mCenterBean = centerBean;
                        String content = "服务经理你好，我想要购买会员兑换码";
                        //IMUtils.singleChat(MakeConvertcodeActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "1",content);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {
                    }
                });
                break;
            default:
                break;

        }
    }

    /**
     * 提交请求数据
     */
    private void summitData() {
        RequestManager.getInstance().redeUseCode(mcodeEt.getText().toString(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                AlertDialogUtils.AlertDialog(MakeConvertcodeActivity.this, msg, "确定", null, new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                        setResult(16);
                        ComUtils.finishshortAll();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MakeConvertcodeActivity.this, msg);
            }
        });
    }

}
