package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.UserInfoBean;
import com.xinniu.android.qiqueqiao.bean.YzmBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.LoginCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.request.callback.YzmCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.ZpPhoneEditText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.MD5;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

/**
 * 登录主界面
 * Created by yuchance on 2018/12/21.
 */

public class LoginNewActivity extends BaseActivity {
    @BindView(R.id.mlogin_phonenum)
    ZpPhoneEditText mloginPhonenum;


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginNewActivity.class);

        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(LoginNewActivity.this);
    }


    @OnClick({R.id.finish_img, R.id.baccountpsw, R.id.bgoToYzmcode, R.id.bwx_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_img:
                finish();
                break;
            case R.id.baccountpsw:
                AccountPswActivity.start(LoginNewActivity.this, mloginPhonenum.getPhoneText());
                finish();
                break;
            case R.id.bgoToYzmcode:
                String phone = mloginPhonenum.getPhoneText();
                if (phone.length() < 11) {
                    ToastUtils.showCentetToast(LoginNewActivity.this, "请输入正确的手机号");
                    return;
                }
                goToGainYzm(phone, 3);
                //  LoginYzmActivity.start(LoginNewActivity.this,phone);//测试专用
                break;
            case R.id.bwx_login:
                wxLogin(SHARE_MEDIA.WEIXIN);
                break;
            default:
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
        Log.d("===YzmHelper", sign.toString());
        String signx = MD5.encrypt(sign.toString(), true);
        Log.d("===YzmHelper", signx);
        showBookingToast(2);
        RequestManager.getInstance().getYzm(phone, type, signx, new YzmCallback() {
            @Override
            public void onSuccess(YzmBean msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(LoginNewActivity.this, msg.msg);
                LoginYzmActivity.start(LoginNewActivity.this, phone);

            }

            @Override
            public void onFailed(String error, int code) {
                dismissBookingToast();
                if (code == 204) {
                    //获取不到动态码，但是信息正确，跳转到验证码界面让他获取语音验证码
                    ToastUtils.showCentetToast(LoginNewActivity.this, error);
                    LoginYzmActivity.start(LoginNewActivity.this, phone);
                } else {
                    ToastUtils.showCentetToast(LoginNewActivity.this, error);
                }
            }
        });


    }

    private void wxLogin(final SHARE_MEDIA media) {
        UMShareAPI.get(this).getPlatformInfo(LoginNewActivity.this, media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String uid = "";
                String name = "";
                String headImg = "";
                String openId = "";
                String sex = "";
                if (map.containsKey("uid")) {
                    uid = map.get("uid");
                }
                if (map.containsKey("openid")) {
                    openId = map.get("openid");
                }
                if (map.containsKey("name")) {
                    name = map.get("name");
                }
                if (map.containsKey("profile_image_url")) {
                    headImg = map.get("profile_image_url");
                }
                if (map.containsKey("sex")) {
                    sex = map.get("sex");
                }
                thirdLogin(uid, name, headImg, media, sex);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                String throwx = throwable.toString();
                if (TextUtils.isEmpty(throwx)) {
                    return;
                }
                if (throwx.contains("2008")) {
                    ToastUtils.showCentetToast(LoginNewActivity.this, "没安装微信应用");
                }

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }

    private void thirdLogin(final String uid, final String name, final String headImg, SHARE_MEDIA media, final String sex) {
        showBookingToast(2);
        int logWay = 0;
        if (media == SHARE_MEDIA.WEIXIN) {
            logWay = 2;
        }
        RequestManager.getInstance().loginByThirdV2(logWay, uid, uid, new LoginCallback() {
            @Override
            public void onSuccess(final UserInfoBean userInfoBean) {
                if (userInfoBean.status == 1) {

                    RequestManager.getInstance().getUserInfo(userInfoBean.user_id, userInfoBean.token, new RequestCallback<DetailedUserInfoBean>() {
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
//                    UserInfoHelper.getIntance().setHeadUrl(userInfoBean.get);
                    CompleteInfoActivity.start(LoginNewActivity.this, userInfoBean.token, userInfoBean.user_id, userInfoBean.rong_token);
                } else {
//                    IMUtils.connectIM(userInfoBean.rong_token, true, new ResultCallback<String>() {
//
//                        @Override
//                        public void onSuccess(String s) {
//                            UserInfoHelper.getIntance().setUserId(userInfoBean.user_id);
//                            UserInfoHelper.getIntance().setToken(userInfoBean.token);
//                            UserInfoHelper.getIntance().setRongyunToken(userInfoBean.getRong_token());
//                            dismissBookingToast();
//                            startActivity(new Intent(LoginNewActivity.this, MainActivity.class));
//
//                            finish();
//                        }
//
//                        @Override
//                        public void onFail(int errorCode) {
//                            ToastUtils.showCentetToast(LoginNewActivity.this, "登录失败");
//                            dismissBookingToast();
//                        }
//
//
//                    });

                    Set<String> PushArray = new HashSet<>();
                    int userId = UserInfoHelper.getIntance().getUserId();
                    PushArray.add(userId + "");
                    JPushInterface.resumePush(getApplicationContext());
                    JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
                    JPushInterface.setTags(getApplicationContext(), 0, PushArray);


                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                if (code == 203) {
                    Intent intent = new Intent(LoginNewActivity.this, BindPhoneActivity.class);
                    intent.putExtra("make", 2);
                    intent.putExtra("openId", uid);
                    intent.putExtra("uid", uid);
                    intent.putExtra("name", name);
                    intent.putExtra("headImg", headImg);
                    intent.putExtra("sex", sex);
                    startActivity(intent);
                } else {
                    ToastUtils.showCentetImgToast(LoginNewActivity.this, msg);
                }

            }
        });
    }

}
