package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.chinatelecom.account.api.CtAuth;
import cn.com.chinatelecom.account.api.ResultListener;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.MD5;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.bgoToLogin)
    TextView bgoToLogin;
    @BindView(R.id.baccountother)
    TextView baccountother;
    @BindView(R.id.baccountpsw)
    TextView baccountpsw;
    @BindView(R.id.bwx_login)
    TextView bwxLogin;
    @BindView(R.id.finish_img)
    LinearLayout finishImg;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.phone_title)
    TextView phoneTitle;
    @BindView(R.id.tv_rz)
    TextView tvRz;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_protocol_01)
    TextView tvProtocol01;
    @BindView(R.id.tv_protocol_02)
    TextView tvProtocol02;
    @BindView(R.id.llayout)
    LinearLayout llayout;
    @BindView(R.id.otherlogin)
    TextView otherlogin;
    @BindView(R.id.otherRl)
    RelativeLayout otherRl;
    @BindView(R.id.rlayout_bottom)
    RelativeLayout rlayoutBottom;
    @BindView(R.id.phone_content)
    TextView phoneContent;
    @BindView(R.id.mlogin_phonenum)
    ZpPhoneEditText mloginPhonenum;

    private String accessCode;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_new;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(LoginActivity.this);
        showBookingToast(1);
        CtAuth.getInstance().requestPreLogin(null, new ResultListener() {
            @Override
            public void onResult(String data) {
                //   ToastUtils.showCentetToast(LoginActivity.this,result);

                Log.i(TAG, "testPreCode ---> result : " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int result = jsonObject.getInt("result");

                    if (result == 0) {
                        //成功
                        String data1 = jsonObject.getString("data");
                        JSONObject object = new JSONObject(data1);
                        accessCode = object.getString("accessCode");
                        String number = object.getString("number");
                        String operatorType = object.getString("operatorType");//CT电信，CU联通，CM移动，UN其他
                        mloginPhonenum.setText(number);
                        mloginPhonenum.clearFocus();
                        tvRz.setVisibility(View.VISIBLE);
                        if (operatorType.equals("CT")) {
                            tvRz.setText("中国电信认证");
                        } else if (operatorType.equals("CU")) {
                            tvRz.setText("中国联通认证");
                        } else if (operatorType.equals("CM")) {
                            tvRz.setText("中国移动认证");
                        } else if (operatorType.equals("UN")) {
                            tvRz.setText("其他认证");
                        }
                        bgoToLogin.setText("本机号码一键登录");
                        baccountother.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);
                        llayout.setVisibility(View.VISIBLE);


                    } else {
                        mloginPhonenum.setText("");
                        tvRz.setVisibility(View.GONE);
                        bgoToLogin.setText("获取短信验证码");
                        baccountother.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        llayout.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    mloginPhonenum.setText("");
                    tvRz.setVisibility(View.GONE);
                    bgoToLogin.setText("获取短信验证码");
                    baccountother.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                    llayout.setVisibility(View.GONE);

                }
                dismissBookingToast();
            }
        });
    }

    @OnClick({R.id.finish_img, R.id.bgoToLogin, R.id.baccountother, R.id.baccountpsw, R.id.bwx_login, R.id.tv_protocol_01, R.id.tv_protocol_02})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_img:
                finish();
                break;
            case R.id.bgoToLogin:
                //一键登录或者获取短信验证码
                if (bgoToLogin.getText().toString().equals("获取短信验证码")) {
                    String phone = mloginPhonenum.getPhoneText();
                    if (phone.length() < 11) {
                        ToastUtils.showCentetToast(LoginActivity.this, "请输入正确的手机号");
                        return;
                    }
                    goToGainYzm(phone, 3);
                } else {

                    CtAuth.getInstance().requestLogin(accessCode, null, new ResultListener() {
                        @Override
                        public void onResult(String data) {
                            Log.i(TAG, "testRequestLogin ---> result : " + data);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(data);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    String responseData = jsonObject.getString("responseData");
                                    JSONObject object = new JSONObject(responseData);
                                    String accessToken = object.getString("accessToken");
                                    //接自己的api

                                } else {
                                    ToastUtils.showCentetToast(LoginActivity.this, "一键登录失败，请重试");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.showCentetToast(LoginActivity.this, "一键登录失败，请重试");
                            }

                        }
                    });
                }
                break;
            case R.id.baccountother:
                LoginNewActivity.start(LoginActivity.this);
                finish();
                break;
            case R.id.baccountpsw:
                AccountPswActivity.start(LoginActivity.this, "");
                finish();
                break;
            case R.id.bwx_login:
                wxLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_protocol_01:
                intent = new Intent(LoginActivity.this, AgreeMentActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_protocol_02:
                Intent intent = new Intent(LoginActivity.this, AgreeMentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("theUrl", "https://e.189.cn/sdk/agreement/detail.do?hidetop=true");
                bundle.putString("thetitle", "天翼账号认证服务协议");
                intent.putExtras(bundle);
                startActivity(intent);
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
                ToastUtils.showCentetToast(LoginActivity.this, msg.msg);
                LoginYzmActivity.start(LoginActivity.this, phone);

            }

            @Override
            public void onFailed(String error, int code) {
                dismissBookingToast();
                if (code == 204) {
                    //获取不到动态码，但是信息正确，跳转到验证码界面让他获取语音验证码
                    ToastUtils.showCentetToast(LoginActivity.this, error);
                    LoginYzmActivity.start(LoginActivity.this, phone);
                } else {
                    ToastUtils.showCentetToast(LoginActivity.this, error);
                }
            }
        });


    }

    private void wxLogin(final SHARE_MEDIA media) {
        UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, media, new UMAuthListener() {
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
                    ToastUtils.showCentetToast(LoginActivity.this, "没安装微信应用");
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
                    CompleteInfoActivity.start(LoginActivity.this, userInfoBean.token, userInfoBean.user_id, userInfoBean.rong_token);
                } else {
                    //IMUtils.connectIM(userInfoBean.rong_token, true, new ResultCallback<String>() {


                        @Override
                        public void onFail(int errorCode) {
                            ToastUtils.showCentetToast(LoginActivity.this, "登录失败");
                            dismissBookingToast();
                        }


                        @Override
                        public void onSuccess(String s) {
                            UserInfoHelper.getIntance().setUserId(userInfoBean.user_id);
                            UserInfoHelper.getIntance().setToken(userInfoBean.token);
                            UserInfoHelper.getIntance().setRongyunToken(userInfoBean.getRong_token());
                            dismissBookingToast();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            finish();
                        }

                    });

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
                    Intent intent = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    intent.putExtra("make", 2);
                    intent.putExtra("openId", uid);
                    intent.putExtra("uid", uid);
                    intent.putExtra("name", name);
                    intent.putExtra("headImg", headImg);
                    intent.putExtra("sex", sex);
                    startActivity(intent);
                } else {
                    ToastUtils.showCentetImgToast(LoginActivity.this, msg);
                }

            }
        });
    }
}
