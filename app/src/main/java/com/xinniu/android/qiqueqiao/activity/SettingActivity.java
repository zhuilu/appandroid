package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.fragment.edit.PhoneEditFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.AppUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by qinlei
 * Created on 2017/12/15
 * Created description :
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tb_return)
    ImageView mTbReturn;
    @BindView(R.id.tb_menu)
    ImageView mTbMenu;
    @BindView(R.id.tb_title)
    TextView mTbTitle;
    @BindView(R.id.ll_change_pwd)
    LinearLayout llChangePwd;
    @BindView(R.id.ll_change_phone)
    LinearLayout llChangePhone;
    @BindView(R.id.ll_change_weixin)
    LinearLayout llChangeWeixin;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    @BindView(R.id.unLoginLl)
    LinearLayout unLoginLl;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.tv_notify_sound)
    TextView tvNotifySound;
    private Call mCall;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initTitleBar();
        ShowUtils.showTextPerfect(tvVersion, "V." + AppUtils.getVersionName());
        String str1 = "您可以在系统设置中开启或关闭提示声音，去<font color='#408AF6'>设置</font";
        tvNotifySound.setText(Html.fromHtml(str1));
        getUserInfo();
    }

    private void initTitleBar() {
        topStatusBar(true);
        mTbReturn.setImageResource(R.mipmap.chevron);
        ShowUtils.showTextPerfect(mTbTitle, "设置");
        mTbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (!UserInfoHelper.getIntance().isLogin()) {
            ShowUtils.showViewVisible(unLoginLl, View.GONE);
            ShowUtils.showViewVisible(llExit, View.GONE);
        } else {
            ShowUtils.showViewVisible(unLoginLl, View.VISIBLE);
            ShowUtils.showViewVisible(llExit, View.VISIBLE);
        }

    }

    @OnClick({R.id.ll_greetings,R.id.ll_change_pwd, R.id.ll_change_phone, R.id.ll_change_weixin, R.id.ll_about, R.id.ll_exit, R.id.ll_help, R.id.ll_notify_sound, R.id.tv_notify_sound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_pwd:
                if (!UserInfoHelper.getIntance().isLogin()) {
                    // TODO: 2017/12/20
                    ToastUtils.showCentetImgToast(this, "还未登录");
                } else {
//                    EditMineInfoActivity.startPwdEdit(this, "修改登录密码");
                    ChangeCodeActivity.start(this);
                }
                break;
            case R.id.ll_change_phone:
                if (!UserInfoHelper.getIntance().isLogin()) {
                    // TODO: 2017/12/20
                    ToastUtils.showCentetImgToast(this, "还未登录");
                } else {
                    EditMineInfoActivity.startPhoneEdit(this, "更换手机号");
                }

                break;
            case R.id.ll_change_weixin:
                if (!UserInfoHelper.getIntance().isLogin()) {
                    // TODO: 2017/12/20
                    ToastUtils.showCentetImgToast(this, "还未登录");
                } else {
                    if ("绑定".equals(tvWeixin.getText().toString())) {
                        auth();
                    }
                }
                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this, AboutQQQActivity.class));
                break;
            case R.id.ll_exit:
//                UserInfoHelper.getIntance().exit();
                BaseApp.getInstance().Logout();
                finish();
//                getUserInfo();
                break;
            case R.id.ll_help:
                HelpActivity.start(SettingActivity.this);
                break;
            case R.id.ll_notify_sound:
                toSelfSetting(mContext);

                break;
            case R.id.tv_notify_sound:
                toSelfSetting(mContext);
                break;
            case R.id.ll_greetings:
              //打招呼语
                GreetingsListActivity.start(mContext);
                break;

            default:
                break;
        }
    }

    public static void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            tvPhone.setText(" ");
            tvWeixin.setText(" ");
            return;
        }
        RequestManager.getInstance().getUserInfo(new RequestCallback<DetailedUserInfoBean>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
            }

            @Override
            public void onSuccess(DetailedUserInfoBean userDetailInfoBean) {
                Logger.d("HTTP", userDetailInfoBean.toString());
                ShowUtils.showTextPerfect(tvPhone, StringUtils.hidePhoneNum(userDetailInfoBean.getMobile()));
//                if (userDetailInfoBean.getBind_wx() == 0) {
                ShowUtils.showTextPerfect(tvWeixin, "绑定");
//                }
            }

            @Override
            public void onFailed(int code, String msg) {
            }

            @Override
            public void requestEnd() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditMineInfoActivity.EDIT_PHONE && resultCode == PhoneEditFragment.EDIT_PHONE_RESULT) {
            getUserInfo();
        }

    }

    private void auth() {
        UMShareAPI.get(this).getPlatformInfo(SettingActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.i("lzq", "授权开始： ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String uid = "";
                if (map.containsKey("uid")) {
                    uid = map.get("uid");
                }
                bindWechat(uid);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    private void bindWechat(String openId) {
        RequestManager.getInstance().bind(1, 2, openId, new RequestCallback<Object>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(2);
            }

            @Override
            public void onSuccess(Object o) {
                ToastUtils.showCentetImgToast(SettingActivity.this, "绑定成功");
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(SettingActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }
}
