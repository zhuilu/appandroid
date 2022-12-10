package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatDialog;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzq on 2018/2/27.
 */

public class InviteFriendActivity extends BaseActivity {
    @BindView(R.id.bt_close)
    RelativeLayout btClose;
    @BindView(R.id.invide_web)
    BridgeWebView invideWeb;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    private String shareUrl;
    private String urlx;

    public static void start(Context context) {
        Intent intent = new Intent(context, InviteFriendActivity.class);
        context.startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        int userId = UserInfoHelper.getIntance().getUserId();
//        invideWeb.setDefaultHandler(new DefaultHandler());
        invideWeb.setWebChromeClient(new WebChromeClient());
        invideWeb.getSettings().setJavaScriptEnabled(true);
//        invideWeb.setWebViewClient(new MyWebViewClient(invideWeb));
        String url = RetrofitHelper.API_URL + "resource/invitation/invitation.html?user_id=" + userId;
        invideWeb.loadUrl(url);
        if (Build.VERSION.SDK_INT >= 19) {
            invideWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        invideWeb.registerHandler("getInviteUrlFromService", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    urlx = object.optString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shareUrl = urlx;
                showShareDialog();

            }
        });
        webprogress(invideWeb,progressBar1);


    }


    @OnClick({R.id.bt_close, R.id.title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                if (invideWeb != null) {
                    if (invideWeb.canGoBack()) {
                        invideWeb.goBack();
                        return;
                    }
                }
                finish();
                break;
        }

    }

    private void showShareDialog() {
        AppCompatDialog shareDialog = new QLShareDialog.Builder(mContext)
                .setWithQRCode(2)
                .setShareCallback(new QLShareDialog.ShareCallback() {
                    @Override
                    public void onClickShare(QLShareDialog.ShareType type) {
                        switch (type) {
                            case SHARE_WEIXIN:
                                shareCircle(SHARE_MEDIA.WEIXIN);
                                break;
                            case SHARE_CIRCLE:
                                shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                break;
                            case SHARE_QQ:
                                shareCircle(SHARE_MEDIA.QQ);
                                break;
                            case SHARE_WEIBO:
                                shareCircle(SHARE_MEDIA.SINA);
                                break;
                            default:

                                break;
                        }
                    }
                }).build();
        shareDialog.show();
    }

    private void shareCircle(SHARE_MEDIA share_media) {
//        if (share_media == SHARE_MEDIA.WEIXIN) {
        String titleString = "送您与企业各大boss直聊合作的机会，快来领取吧!";
        String builder = "谈合作，上企鹊桥。";
        //            ShareUtils.shareWxMini(this, share_media, shareUrl, titleString, builder.toString(), "", new UMShareListener() {
//                @Override
//                public void onStart(SHARE_MEDIA share_media) {
//
//                }
//
//                @Override
//                public void onResult(SHARE_MEDIA share_media) {
//
//                }
//
//                @Override
//                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                }
//
//                @Override
//                public void onCancel(SHARE_MEDIA share_media) {
//
//                }
//            });
//
//
//        } else {
//            if (builder == null || shareUrl == null || headPic == null) {
//                return;
//            }
        String headUrl = UserInfoHelper.getIntance().getHeadUrl();
        ShareUtils.shareWebUrl(
                this,
                headUrl
                , share_media,
                shareUrl,
                titleString.toString(),
                builder.toString(), new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (invideWeb != null) {
            if (invideWeb.canGoBack()) {
                invideWeb.goBack();
                return;
            }
            finish();
        }
    }

}
