package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.RegisterNewBean;
import com.xinniu.android.qiqueqiao.bean.UserInfoBean;
import com.xinniu.android.qiqueqiao.bean.YzmBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.LoginCallback;
import com.xinniu.android.qiqueqiao.request.callback.RegisterCheckCodeCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.request.callback.YzmCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CountDownTextView;
import com.xinniu.android.qiqueqiao.widget.VerificationAction;
import com.xinniu.android.qiqueqiao.widget.VerificationCodeEditText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.MD5;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/12/24.
 */

public class LoginYzmActivity extends BaseActivity {
    @BindView(R.id.myzm_content)
    TextView myzmContent;
    @BindView(R.id.yzmVerEdit)
    VerificationCodeEditText yzmVerEdit;
    @BindView(R.id.mvoiceTv)
    TextView mvoiceTv;
    @BindView(R.id.magreementTv)
    TextView magreementTv;
    @BindView(R.id.bsendYzmtv)
    CountDownTextView bsendYzmtv;
    private String phoneNum;
    private Call mCall;


    public static void start(Context context, String phoneNum) {
        Intent intent = new Intent(context, LoginYzmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phoneNum", phoneNum);

        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login_yzm;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(LoginYzmActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phoneNum = bundle.getString("phoneNum");

            myzmContent.setText(getString(R.string.yzm_content) + ComUtils.phone2star(phoneNum));
        }
        showKeyboard();
        if (bsendYzmtv != null) {
            bsendYzmtv.setCountDownMillis(60000);
            bsendYzmtv.start();
        }
        String yzmContent = "没有收到短信验证码? 请尝试获取语音验证码";
        ComUtils.StringPositionClick(LoginYzmActivity.this, mvoiceTv, yzmContent, 16, yzmContent.length(), ContextCompat.getColor(LoginYzmActivity.this, R.color.vip_info_color), new ComUtils.phoneCallback() {
            @Override
            public void setPhoneCall() {
                goToVoice(phoneNum);
            }
        });
        String agreementStr = "登陆即表示已经阅读并同意《企鹊桥服务协议》";
        ComUtils.StringPositionClick(LoginYzmActivity.this, magreementTv, agreementStr, 12, agreementStr.length(), ContextCompat.getColor(LoginYzmActivity.this, R.color.vip_info_color), new ComUtils.phoneCallback() {
            @Override
            public void setPhoneCall() {
                intent = new Intent(LoginYzmActivity.this, AgreeMentActivity.class);
                startActivity(intent);
            }
        });
        yzmVerEdit.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onInputCompleted(CharSequence s) {
                //    goToLogin(phoneNum, yzmVerEdit.getText().toString());
                checkCode(phoneNum, yzmVerEdit.getText().toString());

            }
        });


    }

    private void checkCode(final String phoneNum, String YzmNum) {
        showBookingToast(2);
        if (ComUtils.isLoginCheckPhoneCode(LoginYzmActivity.this, phoneNum, YzmNum)) {
            RequestManager.getInstance().registerCheckCodeTwo(phoneNum, Integer.parseInt(YzmNum), Constants.lon + "", Constants.lat + "", 1, new RegisterCheckCodeCallback() {
                @Override
                public void onSuccess(final RegisterNewBean bean) {
                    int status = bean.getStatus();//0：直接登录，1：需要完善信息，2：跳转地址页
                    if (status == 0) {
                        RequestManager.getInstance().getUserInfo(bean.getUser_id(), bean.getToken(), new RequestCallback<DetailedUserInfoBean>() {
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
                        //IMUtils.connectIM(bean.getRong_token(), true, new ResultCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                UserInfoHelper.getIntance().setUsername(phoneNum);
                                UserInfoHelper.getIntance().setUserId(bean.getUser_id());
                                UserInfoHelper.getIntance().setToken(bean.getToken());
                                UserInfoHelper.getIntance().setRongyunToken(bean.getRong_token());
                                if (JPushInterface.isPushStopped(getApplicationContext())) {
                                    Set<String> PushArray = new HashSet<>();
                                    int userId = UserInfoHelper.getIntance().getUserId();
                                    PushArray.add(userId + "");
                                    JPushInterface.resumePush(getApplicationContext());
                                    JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
                                    JPushInterface.setTags(getApplicationContext(), 0, PushArray);
                                }
                                dismissBookingToast();
                                startActivity(new Intent(LoginYzmActivity.this, MainActivity.class));
                                ComUtils.finishshortAll();
                            }

                            @Override
                            public void onFail(int errorCode) {
                                ToastUtils.showCentetToast(LoginYzmActivity.this, "聊天服务器连接失败");
                                dismissBookingToast();
                            }
                        });

                    } else if (status == 1) {
                        Constants.newcomer_package = bean.getNewcomer_package();//是否有新人礼包
                        UserInfoHelper.getIntance().setUsername(phoneNum);
                        CompleteInfoActivity.start(LoginYzmActivity.this, bean.getToken(), bean.getUser_id(), bean.getRong_token());
                        finish();
                    } else if (status == 2) {

                        ChooseLocationActivity.start(LoginYzmActivity.this, phoneNum, bean.getCity_name(), bean.getCity_pid(), "", "", 1);
                        finish();
                    }

                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    new QLTipDialog.Builder(LoginYzmActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setPositiveButton("确定", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    if (yzmVerEdit != null) {
                                        yzmVerEdit.setText("");
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams
                                                .SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                                    }

                                }
                            })
                            .show(LoginYzmActivity.this);

//                    ToastUtils.showCentetToast(LoginYzmActivity.this, msg);

                }
            });


        }
    }

    private void goToVoice(String phone) {
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
        RequestManager.getInstance().voiceVerity(phone, signx, 3, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                AlertDialogUtils.AlertDialog(LoginYzmActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {

                    }
                });

            }

            @Override
            public void onFailed(int code, String msg) {
                AlertDialogUtils.AlertDialog(LoginYzmActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {

                    }
                });
            }
        });
    }


    @OnClick({R.id.bt_finish, R.id.bsendYzmtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bsendYzmtv:
                goToGainYzm(phoneNum, 3);
                break;
        }
    }

    private void goToGainYzm(final String phone, int type) {
        String[] signArray = phone.split("");
        Arrays.sort(signArray);
        StringBuffer sign = new StringBuffer();
        for (int i = 0; i < signArray.length; i++) {
            sign.append(signArray[i]);
        }
        sign.append("QiQueqiao2018aySo08pks1k");
        // Log.d("===YzmHelper", sign.toString());
        String signx = MD5.encrypt(sign.toString(), true);
        //   Log.d("===YzmHelper", signx);
        LoginYzmActivity.start(LoginYzmActivity.this, phone);
        showBookingToast(2);
        RequestManager.getInstance().getYzm(phone, type, signx, new YzmCallback() {
            @Override
            public void onSuccess(YzmBean msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(LoginYzmActivity.this, msg.msg);
            }

            @Override
            public void onFailed(String error, int code) {
                dismissBookingToast();
                ToastUtils.showCentetToast(LoginYzmActivity.this, error);
            }
        });
    }

//    private void goToLogin(final String phoneNum, String YzmNum) {
//        showBookingToast(2);
//        if (ComUtils.isLoginCheckPhoneCode(LoginYzmActivity.this, phoneNum, YzmNum)) {
//            RequestManager.getInstance().loginByYzm(phoneNum, YzmNum, new LoginCallback() {
//                @Override
//                public void onSuccess(final UserInfoBean userInfoBean) {
//
//                    RequestManager.getInstance().getUserInfo(userInfoBean.user_id, userInfoBean.token, new RequestCallback<DetailedUserInfoBean>() {
//                        @Override
//                        public void requestStart(Call call) {
//                            mCall = call;
//                        }
//
//                        @Override
//                        public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {
//
//
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//
//                        }
//
//                        @Override
//                        public void requestEnd() {
//
//                        }
//                    });
//                    int status = userInfoBean.status;
//                    if (status == 0) {
//                        //IMUtils.connectIM(userInfoBean.rong_token, true, new ResultCallback<String>() {
//                            @Override
//                            public void onSuccess(String s) {
//                                UserInfoHelper.getIntance().setUsername(phoneNum);
//                                UserInfoHelper.getIntance().setUserId(userInfoBean.user_id);
//                                UserInfoHelper.getIntance().setToken(userInfoBean.token);
//                                UserInfoHelper.getIntance().setRongyunToken(userInfoBean.rong_token);
//                                if (JPushInterface.isPushStopped(getApplicationContext())) {
//                                    Set<String> PushArray = new HashSet<>();
//                                    int userId = UserInfoHelper.getIntance().getUserId();
//                                    PushArray.add(userId + "");
//                                    JPushInterface.resumePush(getApplicationContext());
//                                    JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
//                                    JPushInterface.setTags(getApplicationContext(), 0, PushArray);
//                                }
//                                dismissBookingToast();
//                                startActivity(new Intent(LoginYzmActivity.this, MainActivity.class));
//                                ComUtils.finishshortAll();
//
//                            }
//
//                            @Override
//                            public void onFail(int errorCode) {
//                                ToastUtils.showCentetToast(LoginYzmActivity.this, "聊天服务器连接失败");
//                                dismissBookingToast();
//                            }
//                        });
//
//                    } else if (status == 1) {
//                        Constants.newcomer_package = userInfoBean.getNewcomer_package();//是否有新人礼包
//                        UserInfoHelper.getIntance().setUsername(phoneNum);
//                        CompleteInfoActivity.start(LoginYzmActivity.this, userInfoBean.getToken(), userInfoBean.getUser_id(), userInfoBean.getRong_token());
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onFailed(int code, String msg) {
//                    AlertDialogUtils.AlertDialog(LoginYzmActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
//                        @Override
//                        public void setLeftOnClick(DialogInterface dialog) {
//                            if (yzmVerEdit != null) {
//                                yzmVerEdit.setText("");
//                            }
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void setRightOnClick(DialogInterface dialog) {
//
//                        }
//                    });
//                    dismissBookingToast();
//                }
//            });
//
//
//        }
//    }

    private void showKeyboard() {

        InputMethodManager inputManager =
                (InputMethodManager) mContext.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(yzmVerEdit, 0);


    }


}
