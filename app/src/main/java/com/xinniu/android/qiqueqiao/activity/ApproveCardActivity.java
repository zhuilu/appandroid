package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.alipay.sdk.app.PayTask;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.pay.PayResult;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.PayCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.WechatPayCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.GetPathFromUri;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/7/17.
 * Web页面大全(里面包含所有有关Web与Android交互的功能)
 */

public class ApproveCardActivity extends BaseActivity {
    @BindView(R.id.back_title)
    RelativeLayout backTitle;
    @BindView(R.id.mbridgeWeb)
    BridgeWebView mbridgeWeb;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.mtvTitle)
    TextView mtvTitle;
    @BindView(R.id.bshare_img)
    ImageView bshareImg;
    private htmlBean user = new htmlBean();
    private userBean userBean = new userBean();
    private TokePhotoUtils tokePhotoUtils;
    private String base64bm;
    private String theStr;
    private String bit;
    private String url;
    public static int SHARE_CODE = 500;
    private String webType = "";
    private String urlx;
    private String shareUrl;
    private String urlx1;
    private String title;
    private CenterBean mCenterBean;
    private String headUrl;
    private String builder;
    private String titleString;

    private static final int SDK_PAY_FLAG = 1;

    private WeChatPayBroadcastReceiver broadcastReceiver;
    private String orderId;
    private String shareUrlx;
    private String mWebUrl;
    private String iconType;
    private String posterPageUrl;

    private String act_id;
    private int tokeType = -1;

    public static void start(Context context, String webType) {
        Intent intent = new Intent(context, ApproveCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("webType", webType);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);

    }

    public static void start(Context context, String webType, String url, String title) {
        Intent intent = new Intent(context, ApproveCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("webType", webType);
        bundle.putString("theUrl", url);
        bundle.putString("thetitle", title);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Log.i("lzq", "支付成功");
//                    HashMap<String, Object> mapData = (HashMap<String, Object>) msg.obj;
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    Log.i("Alipay", "payResult : " + payResult.toString());
                    Log.i("Alipay", "resultStatus : " + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showCentetImgToast(ApproveCardActivity.this, "支付成功");
                        savaVoucher(orderId);
                        if (url.contains("luckyDraw")) {
                            startActivity(new Intent(ApproveCardActivity.this, VipV4ListActivity.class));
                            finish();
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(ApproveCardActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(ApproveCardActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(ApproveCardActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };


    private static class MyHandler extends Handler {
        private final WeakReference<ApproveCardActivity> mActivity;
        private setBitmap setBitmap;

        public MyHandler(ApproveCardActivity mActivity) {
            this.mActivity = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            ApproveCardActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 100) {
                    String bit = msg.obj.toString();
                    setBitmap.setBitmap(bit);
                }
            }
        }

        interface setBitmap {
            void setBitmap(String bitmap);
        }


        public void setSetBitmap(MyHandler.setBitmap setBitmap) {
            this.setBitmap = setBitmap;
        }
    }


    private MyHandler handler = new MyHandler(ApproveCardActivity.this);


    @Override
    public int getLayoutId() {
        return R.layout.activity_approve_card;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.getString("theUrl") != null) {
                urlx1 = bundle.getString("theUrl");
                title = bundle.getString("thetitle");

            }
            if (bundle.getString("webType") != null) {
                webType = bundle.getString("webType");
            }
        }
        Intent intent = getIntent();
        Uri uridata = intent.getData();
        try {
            if (uridata != null) {

                if (!TextUtils.isEmpty(uridata.getQueryParameter("act_id"))) {

                    act_id = uridata.getQueryParameter("act_id");
                }
            }
        } catch (NumberFormatException e) {

        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);

        tokePhotoUtils = TokePhotoUtils.getInstance(ApproveCardActivity.this);
        buildWebUrl();
    }

    private void buildWebUrl() {
        mbridgeWeb.setDefaultHandler(new DefaultHandler());
        mbridgeWeb.getSettings().setJavaScriptEnabled(true);

        if (webType.equals("approve")) {
            url = RetrofitHelper.API_URL + "resource/certificate/certificate.html";
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                mbridgeWeb.getSettings().setDomStorageEnabled(true);
                mtvTitle.setText("名片认证");
            }
        } else if (webType.equals("task")) {
            url = RetrofitHelper.API_URL + "resource/task/taskCenter.html";
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mbridgeWeb.getSettings().setDomStorageEnabled(false);
                mtvTitle.setText("任务中心");
            }
        } else if (webType.equals("rule")) {
            url = RetrofitHelper.API_URL + "resource/pages/userguide/reviewrules.html";
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mbridgeWeb.getSettings().setDomStorageEnabled(false);
                mtvTitle.setText("审核规范");
            }
        } else if (webType.equals("vip")) {
            url = urlx1;
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mbridgeWeb.getSettings().setDomStorageEnabled(true);
//                //设置可以访问文件
//                mbridgeWeb.getSettings().setAllowFileAccess(true);
//                //设置支持缩放
//                mbridgeWeb.getSettings().setBuiltInZoomControls(true);
//                //localStorage
//                mbridgeWeb.getSettings().setDomStorageEnabled(true);
//                mbridgeWeb.getSettings().setGeolocationEnabled(true);
                mtvTitle.setText(title);
            }
        } else if (webType.equals("url") || webType.equals("Event")) {
            url = urlx1;
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mbridgeWeb.getSettings().setDomStorageEnabled(true);
//                //设置可以访问文件
//                mbridgeWeb.getSettings().setAllowFileAccess(true);
//                //设置支持缩放
//                mbridgeWeb.getSettings().setBuiltInZoomControls(true);
//                //localStorage
//                mbridgeWeb.getSettings().setDomStorageEnabled(true);
//                mbridgeWeb.getSettings().setGeolocationEnabled(true);
                mtvTitle.setText(title);
            }
        } else {
            url = RetrofitHelper.API_URL + "resource/pages/qqqAct/actdetail.html?id=" + act_id;
            if (Build.VERSION.SDK_INT >= 19) {
                mbridgeWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mbridgeWeb.getSettings().setDomStorageEnabled(false);
                mtvTitle.setText("活动详情");
            }
        }
        url = ComUtils.readWebUrl(url);
        mbridgeWeb.loadUrl(url);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        String mToken = UserInfoHelper.getIntance().getToken();
        final int userId = UserInfoHelper.getIntance().getUserId();
        user.setToken(mToken);
        user.setId(userId + "");


        webprogress(mbridgeWeb, progressBar1);
        mbridgeWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    mtvTitle.setText(title);

                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    ShowUtils.showViewVisible(progressBar1, View.GONE);
                    setListener();
                } else {
                    Log.d("==BaseActivity", "newProgress:" + newProgress);
                    ShowUtils.showViewVisible(progressBar1, View.VISIBLE);
                    progressBar1.setProgress(newProgress);

                }


            }

        });


        setListener();

    }

    private void setListener() {
        //传输用户信息
        mbridgeWeb.callHandler("getUserInfoFromApp", new Gson().toJson(user), new CallBackFunction() {

            @Override
            public void onCallBack(String data) {

            }
        });
        mbridgeWeb.registerHandler("getUserInfoFromAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null) {
                    Log.d("==ApproveCardActivity", data);
                }
                function.onCallBack(new Gson().toJson(user));
            }
        });


        //退出当前页面
        mbridgeWeb.registerHandler("returnApp", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        //上传图片(名片)
        mbridgeWeb.registerHandler("getCardImgFromClient", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                // requestPermission();
                showTakePhoto();
                handler.setSetBitmap(new MyHandler.setBitmap() {
                    @Override
                    public void setBitmap(String bitmap) {
                        userBean.setImgBase(bitmap);
                        theStr = toJson(userBean, 1);
                        function.onCallBack(theStr);
                    }
                });


            }
        });
        mbridgeWeb.registerHandler("callAppShareDrawAct", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    shareUrlx = object.optString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shareUrl = shareUrlx;
                showShareDialog(3);
            }
        });


        mbridgeWeb.registerHandler("viewPersonalHomePageFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    String userId = object.optString("user_id");
                    PersonCentetActivity.start(ApproveCardActivity.this, userId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //判断分享图标
        mbridgeWeb.registerHandler("getIconTypeFromJS", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    iconType = object.optString("iconType");

                    if (iconType.equals("1")) {
                        String title = object.optString("title");
                        String descrip = object.optString("descrip");
                        String image = object.optString("image");
                        String webUrl = object.optString("webUrl");
                        bshareImg.setVisibility(View.VISIBLE);
                        titleString = title;
                        builder = descrip;
                        headUrl = image;
                        if (StringUtils.isEmpty(webUrl)) {
                            shareUrl = url;
                        } else {
                            shareUrl = webUrl;
                        }
//                        if (webType != null) {
//                            if (webType.equals("Event")) {
//                                shareUrl = url;
//                            } else {
//                                shareUrl = webUrl;
//                            }
//                        }

                    } else if (iconType.equals("2")) {
                        String image = object.optString("image");
                        bshareImg.setVisibility(View.VISIBLE);
                        shareUrl = image;
                    } else if (iconType.equals("3")) {
                        String title = object.optString("title");
                        String descrip = object.optString("descrip");
                        String image = object.optString("image");
                        String webUrl = object.optString("webUrl");
                        posterPageUrl = object.optString("posterPageUrl");
                        mWebUrl = webUrl;
                        bshareImg.setVisibility(View.VISIBLE);
                        titleString = title;
                        builder = descrip;
                        headUrl = image;
//                        if (webType != null) {
//                            if (webType.equals("Event")) {
//                                shareUrl = url;
//                            } else {
//                                shareUrl = webUrl;
//                            }
//                        }
                        if (StringUtils.isEmpty(webUrl)) {
                            shareUrl = url;
                        } else {
                            shareUrl = webUrl;
                        }

                    } else {
                        bshareImg.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //刷新页面
        mbridgeWeb.registerHandler("reLoadRequest", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
                start(ApproveCardActivity.this, "approve");
            }
        });
        //去分享至微信
        mbridgeWeb.registerHandler("shareToWechat", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                MyPushActivity.start(ApproveCardActivity.this, SHARE_CODE);
            }
        });
        //去发布资源
        mbridgeWeb.registerHandler("releaseResource", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                goToPublish();
            }
        });
        //去买vip
        mbridgeWeb.registerHandler("buyVip", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                startActivity(new Intent(ApproveCardActivity.this, VipV4ListActivity.class));
                finish();
            }
        });
        mbridgeWeb.registerHandler("getInviteUrlFromService", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    urlx = object.optString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shareUrl = urlx;
                showShareDialog(2);

            }
        });
        mbridgeWeb.registerHandler("callService", new BridgeHandler() {
            @Override
            public void handler(final String data, CallBackFunction function) {
                if (!TextUtils.isEmpty(data)) {
                    try {
                        JSONObject object = new JSONObject(data);
                        final String msg = object.optString("message");
                        int user_id = -1;
                        user_id = object.optInt("user_id", -1);
                        final int finalUser_id = user_id;
                        RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                            @Override
                            public void requestStart(Call call) {
                                showBookingToast(2);
                            }

                            @Override
                            public void onSuccess(CenterBean centerBean) {
                                mCenterBean = centerBean;
                                if (finalUser_id == -1) {
                                    //IMUtils.singleChat(ApproveCardActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "4", msg);
                                } else {
                                    //   //IMUtils.singleChatForResource(ApproveCardActivity.this, finalUser_id+"", "", 0, "", "", "0");
                                    //user_id存在则跳到某个具体的人
                                    //IMUtils.singleChat(ApproveCardActivity.this, finalUser_id + "", "", "4", msg);
                                }
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                ToastUtils.showCentetImgToast(mContext, msg);
                            }

                            @Override
                            public void requestEnd() {
                                dismissBookingToast();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        //我是逗比安卓 我去支付
        mbridgeWeb.registerHandler("callAndroidPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    String payType = object.optString("pay_type");
                    String orderSn = object.optString("order_sn");
                    orderId = object.optString("id");
                    if (payType.equals("1")) {
                        weChatPay(orderSn);
                    } else if (payType.equals("2")) {
                        aliPay(orderSn);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        //去公司主页
        mbridgeWeb.registerHandler("viewCompanyInfoFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    String corporate = object.optString("id");
                    CompanyInfoActivity.start(ApproveCardActivity.this, Integer.parseInt(corporate));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        //去我的活动电子票页面
        mbridgeWeb.registerHandler("callAppReturnTicketPage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    String ticketId = object.optString("ticket_id");
                    GoseeBillActivity.start(ApproveCardActivity.this, Integer.parseInt(ticketId));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        //去发起担保交易界面
        mbridgeWeb.registerHandler("guaranteeTransaction", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!TextUtils.isEmpty(data)) {
                    try {
                        JSONObject object = new JSONObject(data);
                        String obj_user_id = object.optString("obj_user_id");
                        LaunchTransactionAvtivity.start(ApproveCardActivity.this, obj_user_id, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                } else {
                    LaunchTransactionAvtivity.start(ApproveCardActivity.this, "-1", 1);
                }
            }
        });
    }

    private void showTakePhoto() {
        new QLBottomDialog.Builder(this)
                .setNormalColor(R.color._999)
                .setStrOne("拍照")
                .setStrTwo("从手机相册选择")
                .setStrCancel("取消")
                .setBottomDialogItemCallback(new QLBottomDialog.BottomDialogItemCallback() {
                    @Override
                    public void onClick(int position) {
                        switch (position) {
                            case 0:
                                tokeType = 0;
                                break;
                            case 1:
                                tokeType = 1;
                                break;
                        }
                        requestPermission();
                    }
                }).show(ApproveCardActivity.this);
    }

    private void showShareDialog(final int typex) {
        AppCompatDialog shareDialog = new QLShareDialog.Builder(mContext)
                .setWithQRCode(typex)
                .setShareCallback(new QLShareDialog.ShareCallback() {
                    @Override
                    public void onClickShare(QLShareDialog.ShareType type) {
                        switch (type) {
                            case SHARE_WEIXIN:
                                showBookingToast(2);
                                if (typex == 2) {
                                    shareCircle(SHARE_MEDIA.WEIXIN);
                                } else if (typex == 4) {
                                    shareCircle(SHARE_MEDIA.WEIXIN);
                                } else {
                                    shareCirclex(shareUrl, SHARE_MEDIA.WEIXIN);
                                }
                                break;
                            case SHARE_CIRCLE:
                                showBookingToast(2);
                                if (typex == 2) {
                                    shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                } else if (typex == 4) {
                                    shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                } else {
                                    shareCirclex(shareUrl, SHARE_MEDIA.WEIXIN_CIRCLE);
                                }
                                break;
                            case SHARE_QQ:
                                showBookingToast(2);
                                shareCircle(SHARE_MEDIA.QQ);
                                break;
                            case SHARE_WEIBO:
                                showBookingToast(2);
                                shareCircle(SHARE_MEDIA.SINA);
                                break;
                            case SHARE_QRCODE:
                                String str = posterPageUrl.substring(posterPageUrl.indexOf("{"));
                                // ShareNewActivity.start(ApproveCardActivity.this, posterPageUrl, "", "act");
                                ShareNewTwoActivity.start(ApproveCardActivity.this, str, mWebUrl);
                                break;
                            default:

                                break;
                        }
                    }
                }).build();
        shareDialog.show();
    }

    private void goToPublish() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                PublishSelectTypeActivity.start(ApproveCardActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(ApproveCardActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(ApproveCardActivity.this);
                } else if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ApproveCardActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(ApproveCardActivity.this, CompanyEditActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (code == 310) {
                    //未实人认证
                    new QLTipDialog.Builder(ApproveCardActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("去认证", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(ApproveCardActivity.this, 1);
                        }
                    })
                            .show(ApproveCardActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
//                    tokePhotoUtils.cropRawPhoto(ApproveCardActivity.this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    Uri photoUri = tokePhotoUtils.getImgUri();
                    Bitmap bm = decodeFile(photoUri);
                    base64bm = BitmapUtils.bitmapToBase64(bm);
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 100;
                            msg.obj = base64bm;
                            handler.sendMessage(msg);
                        }
                    }.start();
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:
                    Uri photoUri0 = data.getData();
                    String imgUrl = GetPathFromUri.getPathFromUri(mContext, photoUri0);
                    //显示照片
                    final Bitmap bm2 = BitmapUtils.compressScale(imgUrl);
                    base64bm = BitmapUtils.bitmapToBase64(bm2);
                    new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 100;
                            msg.obj = base64bm;
                            handler.sendMessage(msg);
                        }
                    }.start();

                    break;
                default:
                    break;
            }
        }
        if (requestCode == SHARE_CODE && resultCode == MyPushActivity.RESULTCODE) {
            if (webType.equals("task")) {
                finish();
                start(ApproveCardActivity.this, "task");
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == TokePhotoUtils.PERMISSION_TOKE_PHOTO) {
            requestPermission();
        }

    }

    @OnClick({R.id.back_title, R.id.bshare_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_title:
                if (mbridgeWeb != null) {
                    if (mbridgeWeb.canGoBack()) {
                        mbridgeWeb.goBack();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
                break;
            case R.id.bshare_img:
                if (iconType.equals("1")) {
                    showShareDialog(2);
                } else if (iconType.equals("2")) {
                    showShareDialog(3);
                } else if (iconType.equals("3")) {
                    showShareDialog(4);
                }
                break;
            default:
                break;
        }

    }

    public class userBean {
        private String imgBase;

        public void setImgBase(String imgBase) {
            this.imgBase = imgBase;
        }
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

    private String toJson(Object obj, int method) {
        if (method == 1) {
//字段是首字母小写，其余单词首字母大写
            Gson gson = new Gson();
            String obj2 = gson.toJson(obj);
            return obj2;
        } else if (method == 2) {

// FieldNamingPolicy.LOWER_CASE_WITH_DASHES    全部转换为小写，并用空格或者下划线分隔

//FieldNamingPolicy.UPPER_CAMEL_CASE    所以单词首字母大写
            Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            String obj2 = gson2.toJson(obj);
            return obj2;
        }
        return "";
    }


    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(this, TokePhotoUtils.TOKE_PHOTO)) {
//            tokePhotoUtils.tokePhoto(this);
            switch (tokeType) {
                case 0://拍照
                    tokePhotoUtils.tokePhoto(this);
                    break;
                case 1://图册
                    tokePhotoUtils.chooseGallary(this);
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    protected Bitmap decodeFile(Uri uri) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 1280;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE
                        || height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    private void shareCircle(SHARE_MEDIA share_media) {
        String str = "";
        String str1 = "";
        if (StringUtils.isEmpty(titleString)) {
            str = "送您与企业各大boss直聊合作的机会，快来领取吧！";
        } else {
            str = titleString.toString();
        }
        if (StringUtils.isEmpty(builder)) {
            str1 = "谈合作，上企鹊桥。";
        } else {
            str1 = builder.toString();
        }
        if (StringUtils.isEmpty(headUrl)) {
            headUrl = UserInfoHelper.getIntance().getHeadUrl();
        } else {

        }
        ShareUtils.shareWebUrl(
                this,
                headUrl
                , share_media,
                shareUrl,
                str,
                str1, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
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
//        }
    }

    private void shareCirclex(String url, final SHARE_MEDIA share_media) {
        ShareUtils.ShareImg(
                ApproveCardActivity.this,
                share_media,
                url,
                new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
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
    }


    private void aliPay(String orderStr) {
//        String authInfo = "app_id=2017112800216549&method=alipay.trade.app.payAli&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&return_url=&notify_url=http%3A%2F%2Fq.qiqueqiao.com%2Fnotify&timestamp=2017-12-19+17%3A43%3A39&sign=Z9IFhJGWHWOb42LbIOjZmp%2BFH1rHCDJirDGt3jclvEW4fxZ3wLAgfyi9hcu6ixofX7hwdU7Ud3MyCKLAM8dIw%2FdjRnSmuNFMA53u57s0%2F3DGWU2qGr1qJ4cRBTACaGPWG3AOhCRM8x89pGRke44f09dMVBVKkPK2nPjMP4nYec5VaOQrgbf%2BFD8fye85NGGVRQnkCrGdtG6uX5%2FcUePAEFaNIkZ8O30Nh0cU%2BiUTyLlZnhYmdyHkuvoHlcouU5HQ10i4MCMlHuY1IsIxvEu31lAxYQvmJn%2BnZ5k6PU9Qreq4i8g%2BP2cjOvG%2FfGXZ0%2B1KOFsp0B8E67TFSKGXGe0gBw%3D%3D&biz_content=%7B%22out_trade_no%22%3A%22G2017121919766192722696%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22test+subject%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D";

        RequestManager.getInstance().payAli(orderStr, "alipay", new PayCallback() {
            @Override
            public void onSuccess(final String order) {
                /**
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * authInfo的获取必须来自服务端；
                 */

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(ApproveCardActivity.this);
                        Map<String, String> result = alipay.payV2(order, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(ApproveCardActivity.this, msg);
            }
        });
    }

    private void weChatPay(final String orderStr) {
        RequestManager.getInstance().weChatPay(orderStr, "wechat", new WechatPayCallback() {
            @Override
            public void onSuccess(WeChatPayInfo order) {
                Logger.i("lzq", "微信支付成功");
                String weChatAppID = order.getAppid();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(ApproveCardActivity.this, null);
                msgApi.registerApp(weChatAppID);
                PayReq request = new PayReq();
                request.appId = weChatAppID;
                request.partnerId = order.getPartnerid();
                request.prepayId = order.getPrepayid();
                Logger.i("lzq", "微信支付成功" + order.getPackageX());
                request.packageValue = order.getPackageX();
                request.nonceStr = order.getNoncestr();
                request.timeStamp = String.valueOf(order.getTimestamp());
                request.sign = order.getSign();
                msgApi.sendReq(request);

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(ApproveCardActivity.this, msg);
            }
        });
    }

    class WeChatPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("result", -1);
            if (result == 0) {
                savaVoucher(orderId);
                if (url.contains("luckyDraw")) {
                    startActivity(new Intent(ApproveCardActivity.this, VipV4ListActivity.class));
                    finish();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void savaVoucher(String orderId) {
        RequestManager.getInstance().saveVoucher(orderId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ApproveCardActivity.this, msg);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mbridgeWeb.canGoBack()) {
            mbridgeWeb.goBack();
            return true;
        } else {
            finish();
            return true;
        }

    }
}
