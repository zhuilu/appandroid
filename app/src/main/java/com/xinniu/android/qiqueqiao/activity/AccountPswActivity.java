package com.xinniu.android.qiqueqiao.activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.UserInfoBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.LoginCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.ZpPhoneEditText;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/5/2.
 * 账号密码登陆页面
 */

public class AccountPswActivity extends BaseActivity {
    @BindView(R.id.baccountpsw_finishImg)
    RelativeLayout baccountpswFinishImg;
    @BindView(R.id.maccpsw_account_et)
    ZpPhoneEditText maccpswAccountEt;
    @BindView(R.id.maccpsw_psw_et)
    EditText maccpswPswEt;
    @BindView(R.id.baccpsw_login)
    TextView baccpswLogin;

    private Call mCall;

    public static void start(Context context, String phone) {
        Intent intent = new Intent(context, AccountPswActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_accountpsw;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ComUtils.addActivity(AccountPswActivity.this);
        topStatusBar(true);
        String phone = getIntent().getExtras().getString("phone");
        if (!StringUtils.isEmpty(phone) && phone.length() >= 11) {
            maccpswAccountEt.setText(phone);
            maccpswAccountEt.setSelection(phone.length() + 2);
        }
    }


    @OnClick({R.id.baccountpsw_finishImg, R.id.baccpsw_login, R.id.bgoToYzmLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baccountpsw_finishImg:
                finish();
                break;
            case R.id.baccpsw_login:
                if (!ComUtils.isLoginCheckPass(AccountPswActivity.this, maccpswAccountEt.getPhoneText(), maccpswPswEt)) {
                    return;
                }
                showBookingToast(2);
                final String username = maccpswAccountEt.getPhoneText().toString();
                String pwd = maccpswPswEt.getText().toString();
                RequestManager.getInstance().loginByPhone(username, pwd, new LoginCallback() {
                    @Override
                    public void onSuccess(final UserInfoBean userInfoBean) {
//                        requestGetUserInfo();

                        if (userInfoBean.getStatus() == 1) {
                            dismissBookingToast();
                            CompleteInfoActivity.start(AccountPswActivity.this, userInfoBean.token, userInfoBean.user_id, userInfoBean.rong_token);
//                            startActivity(new Intent(AccountPswActivity.this,CompleteInfoActivity.class));
                            finish();
                        } else {
                            UserInfoHelper.getIntance().setUsername(username);
//                            RequestManager.getInstance().center(null);
                            //IMUtils.connectIM(userInfoBean.rong_token, true, new ResultCallback<String>() {

                                @Override
                                public void onFail(int errorCode) {
                                    ToastUtils.showCentetToast(AccountPswActivity.this, "聊天服务器连接失败");
                                    dismissBookingToast();
                                }


                                @Override
                                public void onSuccess(String s) {
                                    UserInfoHelper.getIntance().setUserId(userInfoBean.user_id);
                                    UserInfoHelper.getIntance().setToken(userInfoBean.token);
                                    UserInfoHelper.getIntance().setRongyunToken(userInfoBean.getRong_token());
                                    if (JPushInterface.isPushStopped(getApplicationContext())) {
                                        JPushInterface.resumePush(getApplicationContext());
                                    }
                                    RequestManager.getInstance().getUserInfo(new RequestCallback<DetailedUserInfoBean>() {
                                        @Override
                                        public void requestStart(Call call) {
                                            mCall = call;
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
//                                    if (JPushInterface.isPushStopped(getApplicationContext())){
                                    Set<String> PushArray = new HashSet<>();
                                    int userId = UserInfoHelper.getIntance().getUserId();
                                    PushArray.add(userId + "");
                                    JPushInterface.resumePush(getApplicationContext());
                                    JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
                                    JPushInterface.setTags(getApplicationContext(), 0, PushArray);
//                                    }


                                    dismissBookingToast();
                                    startActivity(new Intent(AccountPswActivity.this, MainActivity.class));
                                    ComUtils.finishshortAll();

                                }

                            });
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        dismissBookingToast();
                        ToastUtils.showCentetToast(AccountPswActivity.this, msg);
                    }
                });


                break;
            case R.id.bgoToYzmLogin:
                LoginNewActivity.start(AccountPswActivity.this);
                finish();
                break;
            default:
                break;
        }
    }

}
