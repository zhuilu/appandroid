package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzq on 2017/12/20.
 * 企鹊桥服务协议页面(也有部分的Web页,如公司官网)
 */

public class AgreeMentActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.agree_ment)
    WebView mWebView;
    @BindView(R.id.bt_close)
    ImageView closeTv;
    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_agree_ment;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        final Bundle bundle  = getIntent().getExtras();
        if (bundle!=null){
            if (bundle.getString("companyUrl")!=null) {
                url = bundle.getString("companyUrl");
                toolBarTitle.setText("公司官网");
            }else {
                url = bundle.getString("theUrl");
                String title = bundle.getString("thetitle");
                toolBarTitle.setText(title);
            }
        }else {
            url = "http://q.qiqueqiao.com/serviceAgreement";
            toolBarTitle.setText("企鹊桥服务协议");
        }
        StatusBarUtil.StatusBarLightMode(this,false);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //        webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        url = ComUtils.readWebUrl(url);
        mWebView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        if (url.contains("sourceid=")) {
                            int x = url.indexOf("sourceid=");
                            int endx = url.indexOf("&");
                            String sourceId = url.substring(x + 9, endx);
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("detailId",Integer.parseInt(sourceId));
                            intent.putExtras(bundle1);
                            startActivity(intent);
                        }
                        return true;
                    }
                }catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }


            }
        });


        topStatusBar(false);

    }

    @OnClick({R.id.bt_close})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.bt_close == id) {
            finish();
        }
    }

}
