package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.RelativeLayout;

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
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/9/14.
 */

public class NewResourceActivity extends BaseActivity {
    @BindView(R.id.back_title)
    RelativeLayout backTitle;
    @BindView(R.id.newrecycler)
    RecyclerView newrecycler;
    @BindView(R.id.companySwipe)
    SmartRefreshLayout companySwipe;

    private LinearLayoutManager manager;
    private int mPage = 1;
    private IndexNewAdapter mIndexFragmentAdapter;
    //首页列表
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NewResourceActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newresource;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        mIndexFragmentAdapter = new IndexNewAdapter(NewResourceActivity.this, R.layout.item_index_new, resourceItems, 0,1);
        manager = new LinearLayoutManager(NewResourceActivity.this);
        newrecycler.setLayoutManager(manager);
        newrecycler.setAdapter(mIndexFragmentAdapter);
        buildDatas(mPage, true);

        companySwipe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                buildDatas(mPage, false);
            }
        });
        companySwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                buildDatas(mPage, false);
            }
        });

    }

    private void buildDatas(final int page, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getNewResourceItem(page, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean bean) {
                finishSwipe();
                dismissBookingToast();
//                if (mPage == 1) {
//                    resourceItems.clear();
//                    mIndexFragmentAdapter.setFooterView(footView);
//
//
//                }
//                resourceItems.addAll(resultDO.getList());
//                mIndexFragmentAdapter.notifyDataSetChanged();
                if (page == 1) {
                    resourceItems.clear();
                    if (bean.getList().size() == 0) {
                        mIndexFragmentAdapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(false);
                    } else {

                        if (bean.getHasMore() == 0) {
                            mIndexFragmentAdapter.setFooterView(footView);
                            companySwipe.setEnableLoadMore(false);
                        } else {
                            mIndexFragmentAdapter.removeAllFooterView();
                            companySwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        mIndexFragmentAdapter.setFooterView(footView);
                        companySwipe.setEnableLoadMore(false);
                    } else {
                        mIndexFragmentAdapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(true);
                    }
                }
                resourceItems.addAll(bean.getList());
                mIndexFragmentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(NewResourceActivity.this, msg);
                dismissBookingToast();
                finishSwipe();
            }
        });

    }

    private void finishSwipe() {
        if (companySwipe != null) {
            if (companySwipe.isEnableRefresh()) {
                companySwipe.finishRefresh(200);
            }
            if (companySwipe.isEnableLoadMore()) {
                companySwipe.finishLoadMore(200);
            }
        }
    }


    @OnClick(R.id.back_title)
    public void onViewClicked() {
        finish();
    }
}
