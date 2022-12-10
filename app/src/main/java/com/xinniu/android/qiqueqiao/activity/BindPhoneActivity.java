package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.RegisterBean;
import com.xinniu.android.qiqueqiao.bean.RegisterNewBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RegisterCallback;
import com.xinniu.android.qiqueqiao.request.callback.RegisterCheckCodeCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

/**
 * Created by lzq on 2017/12/21.
 * 绑定手机号
 */

public class BindPhoneActivity extends BaseActivity {


    @BindView(R.id.bbindp_finishImg)
    ImageView bbindpFinishImg;
    @BindView(R.id.mbindp_account_et)
    EditText mbindpAccountEt;
    @BindView(R.id.mbindp_yz_et)
    EditText mbindpYzEt;
    @BindView(R.id.bresgit_yzm)
    TextView bresgitYzm;
    @BindView(R.id.bbindp_login)
    TextView bbindpLogin;
    private String username;
    private BroadcastReceiver mBroadcastReceiver;
    private String yzm;
    private int make;
    private String openId;
    private String name;
    private String headImg;
    private String sex;
    private int sexI;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bindphone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        make = intent.getIntExtra("make", 0);
        openId = intent.getStringExtra("openId");
        name = intent.getStringExtra("name");
        headImg = intent.getStringExtra("headImg");
        sex = intent.getStringExtra("sex");
        if (!TextUtils.isEmpty(sex)) {
            sexI = Integer.parseInt(sex);
        } else {
            sexI = 0;
        }
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (bresgitYzm == null) {
                    return;
                }
                int i = intent.getIntExtra("countdown", 0);
                if (i >= YzmHelper.MAX_MINUTE || i <= 1) {
                    bresgitYzm.setText("获取验证码");
                    bresgitYzm.setClickable(true);
                    return;
                }
                dismissBookingToast();
                bresgitYzm.setClickable(false);
                bresgitYzm.setText(i + "s");
            }
        };
        IntentFilter intentFilter = new IntentFilter();

        //设置接收广播的类型
        intentFilter.addAction(YzmHelper.ACTION_TYPE_REGISTER);

        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(mBroadcastReceiver, intentFilter);
    }


    @OnClick({R.id.bbindp_finishImg, R.id.bresgit_yzm, R.id.bbindp_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bbindp_finishImg:
                finish();
                break;
            case R.id.bresgit_yzm:
                if (isPhoneheckPass()) {
                    showBookingToast(2);
                    YzmHelper.getInstance().startCountDown(1, username);
                }
                break;
            case R.id.bbindp_login:
                //       bindLogin();
                checkCode();
                break;
            default:
                break;
        }
    }

    private void checkCode() {
        showBookingToast(2);
        if (isRegisterCheckPass()) {

            RequestManager.getInstance().registerCheckCode(username, Integer.parseInt(yzm), Constants.lon + "", Constants.lat + "", 2, openId, openId, new RegisterCheckCodeCallback() {
                @Override
                public void onSuccess(final RegisterNewBean bean) {
                    int status = bean.getStatus();//0：直接登录，1：需要完善信息，2：跳转地址页
                    if (status == 0) {
                        RequestManager.getInstance().getUserInfo(bean.getUser_id(), bean.getToken(), new RequestCallback<DetailedUserInfoBean>() {
                            @Override
                            public void requestStart(Call call) {

                            }

                            @Override
                            public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {


                            }

                            @Override
                            public void onFailed(int code, String msg) {

                            }

                            @Override
                            public void requestEnd() {

                            }
                        });
//                        IMUtils.connectIM(bean.getRong_token(), true, new ResultCallback<String>() {
//
//
//                            @Override
//                            public void onFail(int errorCode) {
//                                ToastUtils.showCentetToast(BindPhoneActivity.this, "聊天服务器连接失败");
//                                dismissBookingToast();
//                            }
//
//                            @Override
//                            public void onSuccess(String s) {
//                                UserInfoHelper.getIntance().setUsername(username);
//                                UserInfoHelper.getIntance().setUserId(bean.getUser_id());
//                                UserInfoHelper.getIntance().setToken(bean.getToken());
//                                UserInfoHelper.getIntance().setRongyunToken(bean.getRong_token());
//                                if (JPushInterface.isPushStopped(getApplicationContext())) {
//                                    Set<String> PushArray = new HashSet<>();
//                                    int userId = UserInfoHelper.getIntance().getUserId();
//                                    PushArray.add(userId + "");
//                                    JPushInterface.resumePush(getApplicationContext());
//                                    JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
//                                    JPushInterface.setTags(getApplicationContext(), 0, PushArray);
//                                }
//                                dismissBookingToast();
//                                startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
//                                ComUtils.finishshortAll();
//
//                            }
//
//                        });
                    } else if (status == 1) {
                        Constants.newcomer_package = bean.getNewcomer_package();//是否有新人礼包
                        UserInfoHelper.getIntance().setUsername(username);
                        CompleteInfoActivity.start(BindPhoneActivity.this, bean.getToken(), bean.getUser_id(), bean.getRong_token());
                        finish();
                    } else if (status == 2) {

                        ChooseLocationActivity.start(BindPhoneActivity.this, username, bean.getCity_name(), bean.getCity_pid(), openId, openId, 2);
                        finish();
                    }

                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(BindPhoneActivity.this, msg);

                }
            });

        }
    }

    private void bindLogin() {
        showBookingToast(2);
        if (isRegisterCheckPass()) {
            RequestManager.getInstance().registerV2(username, yzm, make, name, openId, openId, headImg, sexI, new RegisterCallback() {
                @Override
                public void onSuccess(RegisterBean bean) {
                    dismissBookingToast();
                    UserInfoHelper.getIntance().setUsername(username);
                    Constants.newcomer_package = bean.getNewcomer_package();//是否有新人礼包
                    CompleteInfoActivity.start(BindPhoneActivity.this, bean.getToken(), bean.getUser_id(), bean.getRong_token());
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetImgToast(BindPhoneActivity.this, msg);
                }
            });


        }
    }

    private boolean isPhoneheckPass() {
        username = mbindpAccountEt.getText().toString();

        if (username.length() != 11) {
            ToastUtils.showCentetToast(BindPhoneActivity.this, "手机号格式不正确");
            return false;
        }

        if (StringUtils.isEmpty(mbindpAccountEt.getText())) {
            ToastUtils.showCentetImgToast(BindPhoneActivity.this, "手机号不能为空");
            return false;
        }

        return true;
    }

    private boolean isRegisterCheckPass() {
        username = mbindpAccountEt.getText().toString();
        yzm = mbindpYzEt.getText().toString();
        if (StringUtils.isEmpty(mbindpAccountEt.getText())) {
            ToastUtils.showCentetImgToast(BindPhoneActivity.this, "手机号不能为空");
            return false;
        }
        if (StringUtils.isEmpty(mbindpYzEt.getText())) {
            ToastUtils.showCentetToast(BindPhoneActivity.this, "验证码不能为空");
            return false;
        }
        if (username.length() != 11) {
            ToastUtils.showCentetToast(BindPhoneActivity.this, "手机号格式不正确");
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
