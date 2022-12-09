package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.SplashBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.request.callback.GetLaunchScreenCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class AdsActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;
    private SplashBean mBean = null;
    private CountDownTimer countDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ads;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        //获取广告地址
        com.xinniu.android.qiqueqiao.request.RequestManager.getInstance().getLaunchScreen(new GetLaunchScreenCallback() {
            @Override
            public void onSuccess(SplashBean item) {
                mBean = item;
                if(item!=null&&item.getImg().length()>0) {
                    Glide.with(mContext).load(item.getImg()).into(img);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
        //这里重新生成一个sp，是因为原来有的sp在退出登录时会清空；
        int count = PreferenceHelper.readInt(AdsActivity.this, "splash_prefrence",
                "SPLASH_COUNT", 0);
        PreferenceHelper.write(AdsActivity.this, "splash_prefrence",
                "SPLASH_COUNT", count + 1);
        PreferenceHelper.write(AdsActivity.this, "splash_prefrence",
                "SPLASH_TIME", System.currentTimeMillis());
        //   handler.postDelayed(runnable, 2000);
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("oooooooo", (millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {

                startActivity(new Intent(AdsActivity.this, MainActivity.class));
                finish();

            }
        }.start();

    }

    @OnClick({R.id.img, R.id.tv_ads})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img:
                if (mBean != null && !mBean.equals("null")) {
                    countDownTimer.cancel();
                    Constants.click_ads = 1;
                    ComUtils.goToBannerNext(mContext, mBean.getUrl(), false);
                }
                break;
            case R.id.tv_ads:

                countDownTimer.cancel();
                //跳过
                startActivity(new Intent(AdsActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //跳过
        if (Constants.click_ads == 1) {
            Constants.click_ads =0;
            startActivity(new Intent(AdsActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.fade, R.anim.hold);
        }
    }
}
