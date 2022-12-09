package com.xinniu.android.qiqueqiao.customs.label;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

/**
 * Created by yuchance on 2018/6/8.
 */

public class MyWebViewClient extends BridgeWebViewClient {

    public MyWebViewClient(BridgeWebView webView) {
        super(webView);
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
