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
import com.xinniu.android.qiqueqiao.adapter.SeenMeAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.SeenBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetSeenCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2017/12/8.
 */

public class SeeMeFragment extends LazyBaseFragment {
    @BindView(R.id.seen_me_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.see_me_refresh_layout)
    SmartRefreshLayout mSwipeRefreshLayout;

    LinearLayoutManager manager;
    @BindView(R.id.yperchRl)
    RelativeLayout yperchRl;


    private SeenMeAdapter adapter;
    private List<SeenBean.ListBean> mSeenBeanList;
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seeme;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSeenBeanList = new ArrayList<>();

        manager = new LinearLayoutManager(getActivity());

        adapter = new SeenMeAdapter(getActivity(), R.layout.item_seen_me, mSeenBeanList);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
                page++;
                loadData(page);
            }
        });


    }

    @Override
    protected void lazyLoad() {
        page = 1;
        loadData(page);
    }


    private void loadData(final int page) {

        RequestManager.getInstance().getSeenUser(page, new GetSeenCallback() {
            @Override
            public void onSuccess(SeenBean list) {

                if (page == 1) {
                    mSeenBeanList.clear();
                    if (list.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (list.getHasMore() == 0) {
                            adapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }

                } else {
                    if (list.getHasMore()==0){
                        adapter.setFooterView(footView);
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    }else {
                        adapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(true);
                    }
                }
                mSeenBeanList.addAll(list.getList());
                adapter.notifyDataSetChanged();
//                dissMissDialog();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
//                dissMissDialog();
                ToastUtils.showCentetImgToast(getActivity(), msg);
                finishSwipe();
            }
        });
    }

    private void finishSwipe() {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isEnableRefresh()) {
                mSwipeRefreshLayout.finishRefresh();
            }
            if (mSwipeRefreshLayout.isEnableLoadMore()) {
                mSwipeRefreshLayout.finishLoadMore();
            }
        }
    }

}
