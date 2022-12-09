package com.xinniu.android.qiqueqiao.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.alivc.player.VcPlayerLog;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.customs.FullScreenDialog;
import com.xinniu.android.qiqueqiao.customs.dialog.ACProgressFlower;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * Created by yun_sheng on 2017/8/9.
 * activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();
    protected Intent intent;
    protected Context mContext;
    protected boolean isFinish = false;

    ArrayList<Activity> activities = new ArrayList<>();
    private ACProgressFlower progressDialog;
    private boolean progressShow;
    public View popview;
    public PopupWindow needPhotopop;
    protected FullScreenDialog mDialog;
    protected View footView;
    protected View footView1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehind();
        setContentView(getLayoutId());
        BaseApp.getInstance().addActivitySet(this);
        ButterKnife.bind(this);
        mContext = this;
        activities.add(this);
        beforeInitView();
        footView = getLayoutInflater().inflate(R.layout.view_unload_more,null);
        footView1 = getLayoutInflater().inflate(R.layout.view_unload_more_gray,null);
        initViews(savedInstanceState);
        loadData();
    }

    /**状态栏颜色
     *
     * @param isBlack true是黑色 false是白色
     */
    protected void topStatusBar(boolean isBlack){
        StatusBarUtil.StatusBarLightMode(this,isBlack);
    }






    protected void setBehind() {
    }

    /*
    * 透明status bar
    * */
    public void transStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /*
    * 数据的初始化
    * */
    public void beforeInitView(){}


    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 加载数据
     */
    public void loadData() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
        BaseApp.getInstance().removeActivity(this);
//        if(EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this);//反注册EventBus
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }



    /**
     * @param cls
     */
    public void openActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }

    /**
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void openActivity(Class<?> cls, Bundle bundle){
        Intent intent = new Intent(mContext,cls);
        intent.putExtras(bundle);
        startActivity(intent,bundle);
    }

    public void gotoResult(Class<?> cls,Bundle bundle,int requestCode){
        Intent intent = new Intent(mContext,cls);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode,bundle);
    }
    public void gotoResult(Class<?> cls,int requestCode){
        Intent intent = new Intent(mContext,cls);
        startActivityForResult(intent,requestCode);
    }






    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    public void back(View v) {
        finish();
    }



    public void showLoadingDialog(int res) {
        if(progressDialog!=null  &&  progressDialog.isShowing()){
            progressDialog.cancel();
        }
        progressDialog = new ACProgressFlower.Builder(mContext)
                .text(res == 0 ? "" : getString(res))
                .build();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dissMissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();//
        }
    }

    //融云插入消息
//    protected void insertMessageBase(){
//        RequestManager.getInstance().getNews(new NewsCallback() {
//            @Override
//            public void onSuccess(NewsBean bean) {
//                MessageInfoHelper.getIntance().setNewsBean(bean);
//                TextMessage myTextMessage = new TextMessage(bean.getContent());
//
//
//                Message myMessage = Message.obtain(SplashActivity.GM_ID, Conversation.ConversationType.PRIVATE, myTextMessage);
//
//
//                IMUtils.insertMessage(Conversation.ConversationType.PRIVATE,SplashActivity.GM_ID,SplashActivity.GM_ID,myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                    @Override
//                    public void onSuccess(Message message) {
//                    }
//
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                    }
//                });
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                TextMessage myTextMessage = TextMessage.obtain("企鹊桥官方");
//
//                Message myMessage = Message.obtain(SplashActivity.GM_ID, Conversation.ConversationType.PRIVATE, myTextMessage);
//
//
//                IMUtils.insertMessage(Conversation.ConversationType.PRIVATE,SplashActivity.GM_ID,SplashActivity.GM_ID,myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                    @Override
//                    public void onSuccess(Message message) {
//                    }
//
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                    }
//                });
//            }
//        });
//    }
    public void setPopWindow(int layout,int style){
        popview = getLayoutInflater().inflate(layout,null);
//内容，高度，宽度

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);

        //宽度
//        popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
////        高度
//        popupWindow.setHeight(LayoutParams.FILL_PARENT);
//        显示位置

        needPhotopop = new PopupWindow(popview);
        needPhotopop.setAnimationStyle(style);
        needPhotopop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        needPhotopop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        needPhotopop.setFocusable(true);
        needPhotopop.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.BOTTOM, 0, 0);
        needPhotopop.setBackgroundDrawable(dw);
        //设置背景半透明
        backgroundAlpha(0.5f);

        //关闭事件
        needPhotopop.setOnDismissListener(new popupDismissListener());



        popview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
    }
    public void setPopWindow(int layout){
        popview = getLayoutInflater().inflate(layout,null);
//内容，高度，宽度

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);

        //宽度
//        popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
////        高度
//        popupWindow.setHeight(LayoutParams.FILL_PARENT);
//        显示位置

        needPhotopop = new PopupWindow(popview);
        needPhotopop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        needPhotopop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        needPhotopop.setFocusable(true);
        needPhotopop.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.CENTER, 0, 0);
        needPhotopop.setBackgroundDrawable(dw);
        //设置背景半透明
        backgroundAlpha(0.5f);

        //关闭事件
        needPhotopop.setOnDismissListener(new popupDismissListener());



        popview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
    }

    /**
     * 设置添加屏幕的
     * +
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    protected void dispopwindow(){
        needPhotopop.dismiss();
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     */
    class popupDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }
    public static void webprogress(BridgeWebView webView, final ProgressBar progressBar){
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    ShowUtils.showViewVisible(progressBar, View.GONE);
                }else {
                    Log.d("==BaseActivity", "newProgress:" + newProgress);
                    ShowUtils.showViewVisible(progressBar, View.VISIBLE);
                    progressBar.setProgress(newProgress);
            }

            }
        });

    }
    public static void webprogress(WebView webView, final ProgressBar progressBar){
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    ShowUtils.showViewVisible(progressBar, View.GONE);
                }else {
                    ShowUtils.showViewVisible(progressBar, View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }

            }
        });

    }
    public  void showBookingToast(int type) {
        if (mDialog!=null&&mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (type==1) {
            mDialog = new FullScreenDialog(this,R.style.Them_dialog,R.layout.dialog_test_fullscreen);
        }else if (type==2){
            mDialog = new FullScreenDialog(this,R.style.Them_dialog,R.layout.dialog_load_fullscreen);
        } else {
            mDialog = new FullScreenDialog(this,R.style.Them_dialog,R.layout.dialog_new_fullscreen);
        }
        mDialog.setCanceledOnTouchOutside( false );
        if (!isFinishing()) {
            mDialog.show();
        }

    }
    public void dismissBookingToast(){

        if( mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    protected boolean isLoginState() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            return false;
        }
        return true;
    }


    protected boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

}
