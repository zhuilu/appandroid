package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CircleNewApplyAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CircleApplyBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CircleJoinsCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2018/1/31.
 *
 */

public class CircleNewApplyActivity extends BaseActivity implements CircleNewApplyAdapter.OnCircleNewApplyClikListner{

    private int circleId;
    private List<CircleApplyBean.ListBean> mList = new ArrayList<>();
    private CircleNewApplyAdapter mCircleNewApplyAdapter;
    private LinearLayoutManager manager;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_SwipeRefreshLayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.bt_return)
    ImageView closeBt;
    private int page = 1;

    public static void start(Context context,int circleId){
        Intent intent = new Intent(context,CircleNewApplyActivity.class);
        intent.putExtra("circleId",circleId);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_new_apply;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        circleId = getIntent().getIntExtra("circleId",-1);
        mCircleNewApplyAdapter = new CircleNewApplyAdapter(this,R.layout.item_circle_new_apply,mList);
        mCircleNewApplyAdapter.setOnCircleNewApplyClikListner(this);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mCircleNewApplyAdapter);
        circleJoins(page,true);
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            circleJoins(page++,false);
            }
        });
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void circleJoins(final int page,boolean isShow){
        if (isShow) {
            showBookingToast(2);
        }
        RequestManager.getInstance().circleJoins(page, circleId, new CircleJoinsCallback() {
            @Override
            public void onSuccess(CircleApplyBean list) {
                dismissBookingToast();
                if (page == 1){
                    mList.clear();
                    if (list.getList().size() == 0) {
                        mCircleNewApplyAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        if (list.getHasMore()==0){
                            mCircleNewApplyAdapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        }else {
                            mCircleNewApplyAdapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                }else {

                }
                mList.addAll(list.getList());
                mCircleNewApplyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(CircleNewApplyActivity.this,msg);
            }
        });
    }


    @Override
    public void onRefuse(int id) {
        circleHandle(id,2);
    }

    @Override
    public void onAgree(int id) {
        circleHandle(id,1);
    }
    private void circleHandle(int id,int type){
        RequestManager.getInstance().circleHandle(id, type, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                if (!TextUtils.isEmpty(resultDO.getMsg())){
                    ToastUtils.showCentetImgToast(CircleNewApplyActivity.this,resultDO.getMsg());
                }
                page = 1;
                circleJoins(page,false);
            }

            @Override
            public void onFailed(int code, String msg) {
//                ToastUtils.showCentetImgToast(CircleNewApplyActivity.this,msg);
            }
        });
    }
}
