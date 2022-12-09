package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.NoticeListAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.NoticeBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetNoticeListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2018/2/1.
 */

public class CircleNoticeListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_return)
    ImageView closeBt;
    @BindView(R.id.m_SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private NoticeListAdapter mNoticeListAdapter;
    private List<NoticeBean> mNoticeList = new ArrayList<>();
    private int circleId;
    public static void start(Context context, int circleId){
        Intent intent = new Intent(context,CircleNoticeListActivity.class);
        intent.putExtra("circleId",circleId);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_notice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        circleId = getIntent().getIntExtra("circleId",-1);
        mNoticeListAdapter = new NoticeListAdapter(this,mNoticeList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNoticeListAdapter);
        getNoticeList();
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getNoticeList(){
        showBookingToast(1);
        RequestManager.getInstance().getNoticeList(circleId, new GetNoticeListCallback() {
            @Override
            public void onSuccess(List<NoticeBean> list) {
                dismissBookingToast();
                mSwipeRefreshLayout.setRefreshing(false);
                mNoticeList.clear();
                mNoticeList.addAll(list);
                mNoticeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                mSwipeRefreshLayout.setRefreshing(false);
                ToastUtils.showCentetImgToast(CircleNoticeListActivity.this,msg);
            }
        });
    }

    @Override
    public void onRefresh() {
        getNoticeList();
    }
}
