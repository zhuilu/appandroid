package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.WebViewScreenShotUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/7/18.
 */

public class ShareNewActivity extends BaseActivity {


    @BindView(R.id.mshareWeb)
    BridgeWebView mshareWeb;
    @BindView(R.id.bwx_shareTv)
    TextView bwxShareTv;
    @BindView(R.id.bpyq_shareTv)
    TextView bpyqShareTv;
    @BindView(R.id.balc_shareTv)
    TextView balcShareTv;
    @BindView(R.id.bfinish_back)
    ImageView bfinishBack;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    private htmlBean user = new htmlBean();

    public static void start(Context context,String url,String resourceId,String type) {
        Intent intent = new Intent(context, ShareNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("resourceId",resourceId);
        bundle.putString("type",type);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_share_new;
    }

    @Override
    protected void setBehind() {
        checkSdkVersion();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        requestPermission();
        Bundle bundle = getIntent().getExtras();
        String qrUrl = bundle.getString("url");
        String resourceId = bundle.getString("resourceId");
        String type = bundle.getString("type");
        int userId = UserInfoHelper.getIntance().getUserId();
        mshareWeb.setWebChromeClient(new WebChromeClient());
        // ????????????Js,???????????????
        mshareWeb.getSettings().setJavaScriptEnabled(true);
        //?????????????????????????????????????????????,??????????????????????????????????????????
        mshareWeb.getSettings().setDomStorageEnabled(true);
        //
        mshareWeb.getSettings().setBlockNetworkImage(false);
        mshareWeb.getSettings().setSupportZoom(false);
        mshareWeb.setDrawingCacheEnabled(true);



        if (Build.VERSION.SDK_INT >= 19) {
            //????????????
            mshareWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        String mToken = UserInfoHelper.getIntance().getToken();
        user.setToken(mToken);
        user.setId(userId + "");

        if (type.equals("coop")) {
            mshareWeb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

            });
        }
        String url = qrUrl  ;
        mshareWeb.loadUrl(url);
        mshareWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    dismissBookingToast();
                }else {
                    showBookingToast(1);
                }

            }
        });
        mshareWeb.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){
                    progressBar1.setVisibility(View.GONE);
                    buildListener();
                }else {
                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar1.setProgress(newProgress);
                }
            }
        });
        buildListener();

    }

    private void buildListener() {
        //??????????????????
        mshareWeb.callHandler("getUserInfoFromApp", new Gson().toJson(user), new CallBackFunction() {

            @Override
            public void onCallBack(String data) {

            }
        });
    }


    @OnClick({R.id.bwx_shareTv, R.id.bpyq_shareTv, R.id.balc_shareTv, R.id.bfinish_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bwx_shareTv:
                showBookingToast(2);

                Bitmap bitmap = WebViewScreenShotUtils.ActionScreenshot(ShareNewActivity.this, mshareWeb);
//                if (bitmap!=null) {
//                    dissMissDialog();
//                }
                ShareUtils.ShareLongImg(ShareNewActivity.this, SHARE_MEDIA.WEIXIN, bitmap, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewActivity.this);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.bpyq_shareTv:
                showBookingToast(2);
                Bitmap bitmapx = WebViewScreenShotUtils.ActionScreenshot(ShareNewActivity.this, mshareWeb);

                ShareUtils.ShareLongImg(ShareNewActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, bitmapx, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewActivity.this);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.balc_shareTv:
                Bitmap bitmapc = WebViewScreenShotUtils.ActionScreenshot(ShareNewActivity.this, mshareWeb);
                boolean b = WebViewScreenShotUtils.isS(bitmapc, ShareNewActivity.this);
                if (b) {
                    Toast.makeText(ShareNewActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShareNewActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bfinish_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * ?????????????????????5.0??? ??????enableSlowWholeDocumentDraw ????????????html????????????
     * ???5.0+????????????Android???WebView?????????????????????????????????????????????????????????
     * ??????????????????????????????????????????html????????????????????????????????????????????????????????????html?????????
     * ?????????????????????????????????????????????????????????????????????enableSlowWholeDocumentDraw()??????
     */
    private void checkSdkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
    }

    /**
     * ??????????????????
     */
    private void requestPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //??????WRITE_EXTERNAL_STORAGE??????
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        200);
            }

        }
    }

    /**
     * ?????????????????????
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200:
                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                Log.d(TAG, "writeAcceped--" + writeAccepted);
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mshareWeb.canGoBack()) {
            mshareWeb.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class htmlBean {
        private String id;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
