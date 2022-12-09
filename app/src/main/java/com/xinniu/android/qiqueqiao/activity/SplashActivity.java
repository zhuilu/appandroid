package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 启动界面
 * Created by lzq on 2017/12/22.
 */

public class SplashActivity extends BaseActivity {
    public static final String GM_ID = "-1";


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);


        if (UserInfoHelper.getIntance().getIsFrist()) {
            //第一次进入，跳到引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }
            }, 500);
        }
    }


}
