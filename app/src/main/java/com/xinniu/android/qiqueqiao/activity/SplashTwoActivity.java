package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.PreferenceHelper;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

/**
 * 启动界面
 * Created by lzq on 2017/12/22.
 */

public class SplashTwoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApp.getInstance().addActivitySet(this);
        /*还没有加载布局是睡眠1秒，确保黑屏或白屏效果明显*/
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这块耗时操作可以进行初始化，或者网络请求等，1秒结束后跳转到广告页面
                if (UserInfoHelper.getIntance().getIsFrist()) {
                    //第一次进入，跳到引导页
                    startActivity(new Intent(SplashTwoActivity.this, GuideActivity.class));
                    finish();
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                } else {

                    int count = PreferenceHelper.readInt(SplashTwoActivity.this, "splash_prefrence",
                            "SPLASH_COUNT", 0);

                    long time = PreferenceHelper.readLong(SplashTwoActivity.this, "splash_prefrence",
                            "SPLASH_TIME", 0);
                    //判断一天之内是否已经三次进入广告页
                    if (count == 0 && time == 0) {
                        Intent intent = new Intent(SplashTwoActivity.this, AdsActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                    } else {
                        if (count >= 3) {
                            if(TimeUtils.differentDaysByMillisecond(System.currentTimeMillis(),time)<=1){
                                //是今天已经3次了
                                Intent intent = new Intent(SplashTwoActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fade, R.anim.hold);
                            }else{
                                //不是今天,清除次数
                                PreferenceHelper.write(SplashTwoActivity.this, "splash_prefrence",
                                        "SPLASH_COUNT", 0);
                                Intent intent = new Intent(SplashTwoActivity.this, AdsActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fade, R.anim.hold);
                            }

                        } else {
                            Intent intent = new Intent(SplashTwoActivity.this, AdsActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                        }

                    }
                }
            }
        }, 1000);
    }
}
