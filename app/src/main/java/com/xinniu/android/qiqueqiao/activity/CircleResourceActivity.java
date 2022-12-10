package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2018/1/31.
 */

public class CircleResourceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private long circleId;
    List<IndexNewBean.ListBean> mResourceItemList = new ArrayList<>();
    private IndexNewAdapter mIndexFragmentAdapter ;
    LinearLayoutManager manager;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_SwipeRefreshLayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.bt_return)
    ImageView closeBt;
    private int page = 1;

    public static void start(Context context,long circleId){
        Intent intent = new Intent(context,CircleResourceActivity.class);
        intent.putExtra("circleId",circleId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_resource;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        circleId = getIntent().getLongExtra("circleId",-1);
        mIndexFragmentAdapter = new IndexNewAdapter(this,R.layout.item_index_new,mResourceItemList,0,1);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mIndexFragmentAdapter);
        getCircleResource(page);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getCircleResource(page);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCircleResource(page);
            }
        });
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getCircleResource(final int page){
        showBookingToast(1);
        RequestManager.getInstance().getCircleResource(page, circleId, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                if (page == 1){
                    mResourceItemList.clear();
                    if (resultDO.getList().size() == 0) {
                        mIndexFragmentAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        if (resultDO.getHasMore()==0){
                            mIndexFragmentAdapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        }else {
                            mIndexFragmentAdapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                }else {
                    if (resultDO.getHasMore()==0){
                        mIndexFragmentAdapter.setFooterView(footView);
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        mIndexFragmentAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(true);
                    }
                }
                mResourceItemList.addAll(resultDO.getList());
                mIndexFragmentAdapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetImgToast(CircleResourceActivity.this,msg);
            }
        });
    }

    @Override
    public void onRefresh() {
        getCircleResource(1);
    }
    private void finishSwipe() {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isEnableRefresh()) {
                mSwipeRefreshLayout.finishRefresh(200);
            }
            if (mSwipeRefreshLayout.isEnableLoadMore()) {
                mSwipeRefreshLayout.finishLoadMore(200);
            }
        }
    }

}
