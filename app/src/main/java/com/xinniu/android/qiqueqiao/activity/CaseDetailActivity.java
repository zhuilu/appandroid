package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.widget.NestedScrollView;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CaseBean;
import com.xinniu.android.qiqueqiao.bean.ImgOptionEntity;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCaseDetailCallback;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CaseDetailActivity extends BaseActivity {
    @BindView(R.id.bBack_Rl)
    RelativeLayout bBackRl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.new_tool_bar)
    RelativeLayout newToolBar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.mbridgeWeb)
    BridgeWebView webView;
    @BindView(R.id.llayout_detail)
    LinearLayout llayoutDetail;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;
    @BindView(R.id.tv_bt)
    TextView tvBt;
    private int caseId;
    ArrayList<String> bannerImgsBig = new ArrayList<>();
    private ImageWatcherHelper iwHelper;

    public static void start(Context context, int caseId) {
        Intent starter = new Intent(context, CaseDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("caseId", caseId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_case_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Bundle bundle = getIntent().getExtras();
        caseId = bundle.getInt("caseId");
        showBookingToast(1);
        loadDatax();
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ViewpagerImageActivity.start(CaseDetailActivity.this, bannerImgsBig, 0);
//                //取消原有默认的Activity到Activity的过渡动画
//                overridePendingTransition(R.anim.main_fade_in, 0);
                final List<String> longPictureList = new ArrayList<>();
                longPictureList.add(bannerImgsBig.get(0));
                final int initPosition = 0;
                iwHelper.show(convert(longPictureList),initPosition);
            }
        });
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }

    private void loadDatax() {
        RequestManager.getInstance().getCaseInfo(caseId, new GetCaseDetailCallback() {
            @Override
            public void onSuccess(CaseBean item) {
                dismissBookingToast();
                bannerImgsBig.clear();
                bannerImgsBig.add(item.getImages());
                ImageLoader.loadLocalImg(item.getThumb_img(), img);
                tvBt.setText(item.getTitle());
                webView.getSettings().setJavaScriptEnabled(true);
                if (webView.isHardwareAccelerated())
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                webView.setDefaultHandler(new DefaultHandler());

                webView.setWebChromeClient(new WebChromeClient());

                webView.setWebViewClient(new MyWebViewClient(webView));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WebView.setWebContentsDebuggingEnabled(true);
                }
                webView.loadData("<style> img{ width:100%; height:auto;} </style>" + item.getDetails(), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(CaseDetailActivity.this, msg);

            }
        });
    }

    @OnClick(R.id.bBack_Rl)
    public void onClick() {
        finish();
    }

    public class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
            return true;
        }

    }
}
