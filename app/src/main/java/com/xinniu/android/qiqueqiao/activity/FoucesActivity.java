package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CareAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CareBean;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCareListCallback;
import com.xinniu.android.qiqueqiao.widget.EndLessOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description :
 */

public class FoucesActivity extends BaseActivity {
    @BindView(R.id.tb_return)
    ImageView tbReturn;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tb_menu)
    ImageView tbMenu;

    @BindView(R.id.care_me_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.care_me_refresh_layout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private CareAdapter adapter;
    private List<CareBean.ListBean> careList;

    private int page = 1;


    public static void start(Context context) {
        Intent starter = new Intent(context, FoucesActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fouces;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initTitleBar();
        careList = new ArrayList<>();
        adapter = new CareAdapter(FoucesActivity.this,R.layout.item_care, careList);
        LinearLayoutManager manager = new LinearLayoutManager(FoucesActivity.this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                loadCareData(page,false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadCareData(page,false);
            }
        });
        loadCareData(page,true);

    }

    private void initTitleBar() {
        topStatusBar(true);
        tbReturn.setImageResource(R.mipmap.chevron);
        tbTitle.setText("关注");
        tbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadCareData(final int page,boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getCareList(page, new GetCareListCallback() {
            @Override
            public void onSuccess(CareBean list) {
                if (page == 1) {
                    careList.clear();
                    if (list.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        yperchRl.setVisibility(View.GONE);
                        if (list.getHasMore()==0){
                            adapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        }else {
                            adapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                }else {
                    if (list.getHasMore()==0){
                        adapter.setFooterView(footView);
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(true);
                    }
                }

                careList.addAll(list.getList());
                adapter.notifyDataSetChanged();
                dismissBookingToast();
               finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
            }
        });
    }

    private void finishSwipe()
    {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isEnableRefresh()) {
                mSwipeRefreshLayout.finishRefresh();
            }
            if (mSwipeRefreshLayout.isEnableLoadMore()){
                mSwipeRefreshLayout.finishLoadMore();
            }

        }
    }
}
