package com.xinniu.android.qiqueqiao.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.customs.FullScreenDialog;
import com.xinniu.android.qiqueqiao.customs.dialog.ACProgressFlower;
//import com.xinniu.android.qiqueqiao.event.EventBus;

import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yun_sheng
 */
public abstract class LazyBaseFragment extends PermissionFragment {

    protected View rootView;
    protected Context mContext;
    protected Intent intent;
    protected boolean isFinish = false;
    protected Bundle activityBundle;//获取来自activity的数据，使用时需要判断是否为空
    protected boolean progressShow;
    protected ACProgressFlower progressDialog;
    //列表相关  recyclerview
    protected int lastVisibleItem;
    protected boolean isCanloadMore;
    protected FullScreenDialog mDialog;
    protected View footView;
    protected View gfootView;


    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false, isFirst = true, isCreate = false;
    private Unbinder bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        activityBundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
         //  rootView = inflater.inflate(contentViewId(), container, false);
            rootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        }

        footView = LayoutInflater.from(mContext).inflate(R.layout.view_unload_more, null);
        gfootView = LayoutInflater.from(mContext).inflate(R.layout.view_unload_more_gray, null);
        bind = ButterKnife.bind(this, rootView);
        isCreate = true;

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        if (isVisible && isFirst && isCreate) {
            isFirst = false;//是否第一次加载
            lazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisible && isFirst && isCreate) {
            isFirst = false;//是否第一次加载
            lazyLoad();
        }
    }

    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);


    /**
     * 延迟加载
     */
    protected abstract void lazyLoad();

    /**
     * 在onCreateView初始化内容
     *
     * @param res
     */


    public void showLoadingDialog(int res) {
        if (progressDialog != null && progressDialog.isShowing()) {
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

    public void showBookingToast(int type) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (type == 1) {
            mDialog = new FullScreenDialog(mContext, R.style.Them_dialog, R.layout.dialog_booknull_screen);
        } else if (type == 2) {
            mDialog = new FullScreenDialog(mContext, R.style.Them_dialog, R.layout.dialog_load_fullscreen);
        } else if (type == 3) {
            mDialog = new FullScreenDialog(mContext, R.style.Them_dialog1, R.layout.dialog_test_fullscreen2);
        } else {
            mDialog = new FullScreenDialog(mContext, R.style.Them_dialog, R.layout.dialog_booknull_screen);
        }

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void dismissBookingToast() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    public void dissMissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFinish = true;
//        if(EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this);//反注册EventBus
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity()); //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity()); //统计时长
    }
}
