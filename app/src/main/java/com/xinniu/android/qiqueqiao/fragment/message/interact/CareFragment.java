package com.xinniu.android.qiqueqiao.fragment.message.interact;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CareAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.CareBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCareListCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2017/12/8.
 */

public class CareFragment extends LazyBaseFragment {
    @BindView(R.id.care_me_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.care_me_refresh_layout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private CareAdapter adapter;
    private List<CareBean.ListBean> careList;
    private LinearLayoutManager manager;
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_care;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        careList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity());
        adapter = new CareAdapter(getActivity(), R.layout.item_care, careList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                loadData(page);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(page++);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        page = 1;
        loadData(page);
    }

    private void loadData(final int page) {
//        showLoadingDialog(0);
        RequestManager.getInstance().getCareList(page, new GetCareListCallback() {
            @Override
            public void onSuccess(CareBean list) {
                if (page == 1) {
                    careList.clear();
                    if (list.getList().size() == 0) {
                        ShowUtils.showViewVisible(yperchRl,View.VISIBLE);
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    } else {
                        ShowUtils.showViewVisible(yperchRl,View.GONE);
                        if (list.getHasMore() == 0) {
                            adapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    ShowUtils.showViewVisible(yperchRl,View.GONE);
                    if (list.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(true);
                    }
                }
                careList.addAll(list.getList());
                adapter.notifyDataSetChanged();
//                dissMissDialog();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
//                dissMissDialog();
                finishSwipe();
            }
        });
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
