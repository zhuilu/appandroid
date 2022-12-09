package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/7/19.
 */

public class HuntServiceActivity extends BaseActivity {
    @BindView(R.id.tb_return)
    ImageView tbReturn;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.huntWeb)
    BridgeWebView huntWeb;
    @BindView(R.id.tb_menu)
    ImageView tbMenu;


    public static void start(Context context) {
        Intent starter = new Intent(context, HuntServiceActivity.class);

        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hunt_service;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        tbReturn.setImageResource(R.mipmap.chevron);
        tbTitle.setText("专属资源猎头服务");
        huntWeb.setDefaultHandler(new DefaultHandler());
        huntWeb.getSettings().setJavaScriptEnabled(true);
        String url = RetrofitHelper.API_URL + "resource/vip/introduce.html";
        huntWeb.loadUrl(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            huntWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        huntWeb.getSettings().setDomStorageEnabled(true);

    }


    @OnClick(R.id.tb_return)
    public void onViewClicked() {
      finish();
    }
}
